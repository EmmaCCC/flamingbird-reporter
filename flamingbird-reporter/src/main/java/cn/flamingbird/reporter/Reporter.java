package cn.flamingbird.reporter;

import java.util.Map;

public interface Reporter {

    ReporterType getReporterType();

    void sendMessage(String summary, Map<String, String> map);

    void sendMessage(String summary, String content);
}
