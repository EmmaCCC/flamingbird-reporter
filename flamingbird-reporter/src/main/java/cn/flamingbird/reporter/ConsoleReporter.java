package cn.flamingbird.reporter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConsoleReporter extends AbstractReporter {

    public ConsoleReporter(ReporterRegistry registry) {
        super(registry);
    }

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

    @Override
    public void report(Throwable throwable, HttpServletRequest request) {

    }
}
