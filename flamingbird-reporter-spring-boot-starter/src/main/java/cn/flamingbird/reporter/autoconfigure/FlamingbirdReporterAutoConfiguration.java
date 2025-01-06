package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ConsoleReporter;
import cn.flamingbird.reporter.Reporter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ReporterProperties.class)
public class FlamingbirdReporterAutoConfiguration {


//    @Bean
//    public FlamingbirdReporterRegistryProcessor flamingbirdReporterRegistryProcessor(
//            ReporterProperties reporterProperties) {
//        return new FlamingbirdReporterRegistryProcessor(null);
//    }


    @Bean
    public ReporterManager reporterManager(ReporterProperties reporterProperties) {
        return new ReporterManager();
    }

    @Bean
    public Reporter reporter(ConfigurableListableBeanFactory configurableListableBeanFactory,
                             ReporterProperties reporterProperties) {
//        Map<String, Reporter> beansOfType = configurableListableBeanFactory.getBeansOfType(Reporter.class);
        return new ConsoleReporter();
    }


}
