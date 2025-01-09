package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.Reporter;
import cn.flamingbird.reporter.ReporterRegistry;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Configuration
@EnableConfigurationProperties(ReporterProperties.class)
@ConditionalOnProperty(prefix = "flamingbird.reporter", name = "instances", matchIfMissing = true)
public class FlamingbirdReporterAutoConfiguration {

    private ReporterProperties reporterProperties;

    public FlamingbirdReporterAutoConfiguration(ReporterProperties reporterProperties) {
        if (reporterProperties == null || reporterProperties.getInstances() == null
                || reporterProperties.getInstances().isEmpty()) {
            throw new RuntimeException("reporterProperties is null or empty");
        }

        this.reporterProperties = reporterProperties;
    }

    @Bean
    @Primary
    public Reporter reporter(ConfigurableListableBeanFactory beanFactory) {
        //如果有默认实例或者实例只有一个，则直接返回bean
        List<ReporterRegistry> instances = reporterProperties.getInstances();
        ReporterRegistry primary = reporterProperties.getInstances()
                .stream().filter(item -> Objects.equals(item.getName(), "default"))
                .findFirst().orElse(null);
        if (primary == null) {
            primary = reporterProperties.getInstances().stream().findFirst().get();
        }
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
    public ReporterManager reporterManager(ApplicationContext context) {
        Map<String, Reporter> reporters = context.getBeansOfType(Reporter.class);
        return new ReporterManager(reporters);
    }


}
