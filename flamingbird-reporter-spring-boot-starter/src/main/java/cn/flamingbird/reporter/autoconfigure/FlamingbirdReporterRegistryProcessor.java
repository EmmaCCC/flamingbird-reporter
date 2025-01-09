package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.Reporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

public class FlamingbirdReporterRegistryProcessor implements BeanDefinitionRegistryPostProcessor {


    private ReporterProperties reporterProperties;

    public FlamingbirdReporterRegistryProcessor(Environment environment) {
        reporterProperties = Binder.get(environment).bind("flamingbird.reporter", Bindable.of(ReporterProperties.class))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (reporterProperties == null || reporterProperties.getInstances() == null) {
            return;
        }

        //遍历配置中的实例
        reporterProperties.getInstances().forEach((instance) -> {
            Reporter reporter = Reporter.createReporter(instance);
            if (reporter != null) {
                // 动态注册 Bean
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                        .genericBeanDefinition(Reporter.class,
                                () -> reporter)
                        .getBeanDefinition();
                registry.registerBeanDefinition(instance.getName(), beanDefinition);
            }
        });
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        ReporterProperties bean = configurableListableBeanFactory.getBean(ReporterProperties.class);
    }
}
