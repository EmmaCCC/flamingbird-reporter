package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.Reporter;
import cn.flamingbird.reporter.ReporterInterceptor;
import cn.flamingbird.reporter.ReporterRegistry;
import cn.flamingbird.utils.HostEnvironment;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Configuration
@EnableConfigurationProperties(ReporterProperties.class)
@ConditionalOnProperty(prefix = "flamingbird.reporter", name = "instances", matchIfMissing = true)
public class FlamingbirdReporterAutoConfiguration {

    private final ReporterProperties reporterProperties;

    private final ApplicationContext applicationContext;

    private final List<ReporterInterceptor> reporterInterceptors;

    public FlamingbirdReporterAutoConfiguration(ReporterProperties reporterProperties,
                                                ApplicationContext applicationContext,
                                                List<ReporterInterceptor> reporterInterceptors) {
        this.applicationContext = applicationContext;
        this.reporterInterceptors = reporterInterceptors == null ? new ArrayList<>() : reporterInterceptors;
        if (reporterProperties == null || reporterProperties.getInstances() == null
                || reporterProperties.getInstances().isEmpty()) {
            throw new RuntimeException("reporterProperties is null or empty");
        }

        this.reporterProperties = reporterProperties;
    }

    @Bean
    public HostEnvironment hostEnvironment() {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        String profile = Arrays.stream(applicationContext.getEnvironment().getActiveProfiles()).collect(Collectors.joining(","));
        return new HostEnvironment(profile, applicationName);
    }


    @Bean
    @Primary
    public Reporter reporter(ConfigurableListableBeanFactory beanFactory) {
        //如果有默认实例或者实例只有一个，则直接返回bean
        List<ReporterRegistry> instances = reporterProperties.getInstances();

        HostEnvironment hostEnvironment = hostEnvironment();
        reporterInterceptors.add(0, new DefaultReporterInterceptor(hostEnvironment));

        ReporterRegistry primary = reporterProperties.getDefaultInstance();
        if (primary == null) {
            primary = instances
                    .stream().filter(item -> Objects.equals(item.getName(), "default"))
                    .findFirst().orElse(null);
        }
        if (primary == null) {
            primary = instances.stream().findFirst().get();
        }

        instances.add(0, primary);
        instances.forEach(item -> {
            List<ReporterInterceptor> interceptors = reporterInterceptors.stream().filter(interceptor -> interceptor.canUse(item)).collect(Collectors.toList());
            item.setReporterInterceptors(interceptors);
        });

        //遍历配置中的实例
        for (ReporterRegistry instance : instances) {
            //排除主实例，注册其他实例
            if (!Objects.equals(instance.getName(), primary.getName())) {
                Reporter reporter = Reporter.createReporter(instance);
                if (reporter != null) {
                    // 动态注册 Bean
                    beanFactory.registerSingleton(instance.getName(), reporter);
                }
            }
        }
        return Reporter.createReporter(primary);
    }

    @Bean
    public ReporterManager reporterManager() {
        Map<String, Reporter> reporters = applicationContext.getBeansOfType(Reporter.class);
        return new ReporterManager(reporters);
    }


    /**
     * 配置缓存请求体响应体内容，可以记录请求体响应内容
     *
     * @return
     */
    @Bean
    public OncePerRequestFilter cacheRequestBodyFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper(response);
                filterChain.doFilter(cachedRequest, cachedResponse);
                // 重置响应，将内容重新写回响应
                response.getOutputStream().write(cachedResponse.getContentAsByteArray());
            }
        };
    }
}
