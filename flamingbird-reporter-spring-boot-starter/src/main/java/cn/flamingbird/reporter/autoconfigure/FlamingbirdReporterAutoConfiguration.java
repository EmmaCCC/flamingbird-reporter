package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ConsoleReporter;
import cn.flamingbird.reporter.Reporter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
@EnableConfigurationProperties(ReporterProperties.class)
public class FlamingbirdReporterAutoConfiguration {


    @Resource
    ReporterProperties reporterProperties;

    @Bean
    public Reporter reporter() {
        return new ConsoleReporter();
    }
}
