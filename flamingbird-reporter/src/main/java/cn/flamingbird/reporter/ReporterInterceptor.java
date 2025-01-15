package cn.flamingbird.reporter;

public interface ReporterInterceptor {

    boolean canUse(ReporterRegistry registry);

    void beforeReport(ReportContext context);

    void afterReport(ReportContext context);
}
