package cn.flamingbird.reporter;

public interface ReporterFilter {

    void beforeReport(ReportContext context);

    void afterReport(ReportContext context);
}
