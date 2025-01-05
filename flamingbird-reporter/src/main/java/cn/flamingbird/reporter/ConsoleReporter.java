package cn.flamingbird.reporter;

import java.util.Map;

public class ConsoleReporter implements Reporter {

    @Override
    public ReporterType getReporterType() {
        return ReporterType.CONSOLE;
    }

    @Override
    public void sendMessage(String summary, Map<String, String> map) {
        System.out.println(summary + map);
    }

    @Override
    public void sendMessage(String summary, String content) {
        System.out.println(summary + "," + content);
    }
}
