package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ReporterRegistry;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("flamingbird.reporter")
public class ReporterProperties {

    private boolean enabled;
    //    private Map<String, ReporterRegistry> instances;
    private List<ReporterRegistry> instances;
}
