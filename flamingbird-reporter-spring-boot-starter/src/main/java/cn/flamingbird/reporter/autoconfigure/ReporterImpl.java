package cn.flamingbird.reporter.autoconfigure;

import lombok.Data;

@Data
public class ReporterImpl {
    private String name;
    private String url;
    private ReporterType type;
}