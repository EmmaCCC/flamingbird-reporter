package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ConsoleReporter;
import cn.flamingbird.reporter.Reporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FlamingbirdReporterAutoConfiguration {


    @Bean
    public Reporter reporter() {
        return new ConsoleReporter();
    }
}
