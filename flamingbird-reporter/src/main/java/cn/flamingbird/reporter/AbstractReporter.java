package cn.flamingbird.reporter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AbstractReporter implements Reporter {

    protected final ReporterRegistry registry;

    public AbstractReporter(ReporterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ReporterType getReporterType() {
        return null;
    }

    @Override
    public void report(String summary, Map<String, String> map) {
        ReportContext reportContext = new ReportContext();
        reportContext.setSummary(summary);
        reportContext.setMap(map);
        reportContext.setReporter(this);
        this.doReport(reportContext);
    }

    @Override
    public void report(String summary, String content) {
        ReportContext reportContext = new ReportContext();
        reportContext.setSummary(summary);
        reportContext.setContent(content);
        reportContext.setReporter(this);

        this.doReport(reportContext);
    }

    @Override
    public void report(Throwable throwable, HttpServletRequest request) {
        ReportContext reportContext = new ReportContext();
        reportContext.setReporter(this);
        reportContext.setThrowable(throwable);
        reportContext.setRequest(request);
        reportContext.setMap(new LinkedHashMap<>());
        reportContext.setSummary(ReportItems.DEFAULT_SUMMARY);

        Map<String, String> map = reportContext.getMap();
        map.put(ReportItems.REPORT_TIME, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        this.doReport(reportContext);
    }


    protected void prepareReport(ReportContext reportContext) {


    }

    protected void executeReport(ReportContext reportContext) {


    }

    protected void doReport(ReportContext reportContext) {
        try {
            this.doBeforeReportInterceptor(reportContext);
            this.prepareReport(reportContext);
            this.executeReport(reportContext);
        } catch (Exception e) {
            reportContext.setException(e);
        } finally {
            this.doAfterReportInterceptor(reportContext);
        }

    }

    private void doBeforeReportInterceptor(ReportContext reportContext) {
        List<ReporterInterceptor> reporterInterceptors = registry.getReporterInterceptors();
        if (reporterInterceptors != null) {
            for (int i = 0; i < reporterInterceptors.size(); i++) {
                ReporterInterceptor reporterInterceptor = reporterInterceptors.get(i);
                reporterInterceptor.beforeReport(reportContext);
            }
        }
    }

    private void doAfterReportInterceptor(ReportContext reportContext) {
        List<ReporterInterceptor> reporterInterceptors = registry.getReporterInterceptors();
        if (reporterInterceptors != null) {
            for (int i = reporterInterceptors.size() - 1; i >= 0; i--) {
                ReporterInterceptor reporterInterceptor = reporterInterceptors.get(i);
                reporterInterceptor.afterReport(reportContext);
            }
        }
    }
}
