package cn.flamingbird.reporter;

import lombok.Data;

@Data
public class ReporterRegistry {
    private String name;
    private String url;
    private ReporterType type;
}
