package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ConsoleReporter;
import cn.flamingbird.reporter.FeishuReporter;
import cn.flamingbird.reporter.Reporter;
import cn.flamingbird.reporter.ReporterImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
@EnableConfigurationProperties(ReporterProperties.class)
public class FlamingbirdReporterAutoConfiguration implements BeanFactoryPostProcessor {

    @Resource
    private ReporterProperties reporterProperties;

    @Bean
    public Reporter reporter() {
        return new ConsoleReporter();
    }

    private Reporter createReporter(ReporterImpl instance) {
        // 根据配置创建不同类型的 Reporter 实例
        switch (instance.getType()) {
            case FEISHU:
                return new FeishuReporter(instance.getName(), instance.getUrl());
            case DINGDING:
                return null; // 假设 DingdingReporter 构造函数没有参数
            case WORKWX:
                return null;
            default:
                return new ConsoleReporter(); // 假设 ConsoleReporter 构造函数需要参数
        }
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerSingleton("errorReporter", new FeishuReporter("a", "s"));
        if (reporterProperties == null || reporterProperties.getInstances() == null) {
            return;
        }

        //遍历配置中的实例
        reporterProperties.getInstances().forEach((name, instance) -> {
            Reporter reporter = createReporter(instance);
            if (reporter != null) {
                configurableListableBeanFactory.registerSingleton(name, reporter);
//                // 动态注册 Bean
//                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
//                        .genericBeanDefinition(Reporter.class,
//                                () -> reporter)
//                        .getBeanDefinition();
//                registry.registerBeanDefinition(instance.getName(), beanDefinition);
            }
        });
    }
}
