package cn.flamingbird.reporter;

import java.util.Map;

public class ConsoleReporter implements Reporter {

    @Override
    public String getName() {
        return getReporterType().name();
    }

    @Override
    public ReporterType getReporterType() {
        return ReporterType.CONSOLE;
    }

    @Override
    public void report(String summary, Map<String, String> map) {
        System.out.println(summary + map);
    }

    @Override
    public void report(String summary, String content) {
        System.out.println(summary + "," + content);
    }
}
