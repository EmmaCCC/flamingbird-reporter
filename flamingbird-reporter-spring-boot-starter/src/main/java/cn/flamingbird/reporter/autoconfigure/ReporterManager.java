package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.Reporter;

import java.util.Map;
import java.util.stream.Collectors;

public class ReporterManager {

    private final Map<String, Reporter> reporters;

    public ReporterManager(Map<String, Reporter> reporters) {
        this.reporters = reporters.values().stream().collect(Collectors.toMap(Reporter::getName, reporter -> reporter));
    }

    public Reporter getReporter(String name) {
        return reporters.get(name);
    }

}
