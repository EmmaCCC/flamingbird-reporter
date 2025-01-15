package cn.flamingbird.reporter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface Reporter {

    String getName();

    ReporterType getReporterType();

    void report(String summary, Map<String, String> map);

    void report(String summary, String content);

    void report(Throwable throwable, HttpServletRequest request);

    default void report(Throwable throwable) {
        report(throwable, null);
    }

    default void report(HttpServletRequest request) {
        report(null, request);
    }

    static Reporter createReporter(ReporterRegistry instance) {
        // 根据配置创建不同类型的 Reporter 实例
        switch (instance.getType()) {
            case FEISHU:
                return new FeishuReporter(instance);
            case DINGDING:
                return null; // 假设 DingdingReporter 构造函数没有参数
            case WORKWX:
                return null;
            default:
                return new ConsoleReporter(instance); // 假设 ConsoleReporter 构造函数需要参数
        }
    }
}
