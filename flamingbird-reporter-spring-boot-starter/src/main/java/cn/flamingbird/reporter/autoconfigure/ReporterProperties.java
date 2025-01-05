package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ReporterImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("flamingbird.reporter")
public class ReporterProperties {

    private boolean enabled;
    private Map<String, ReporterImpl> instances;
}
