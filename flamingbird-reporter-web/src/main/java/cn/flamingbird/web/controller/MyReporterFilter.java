package cn.flamingbird.web.controller;

import cn.flamingbird.reporter.ReportContext;
import cn.flamingbird.reporter.ReporterFilter;
import org.springframework.stereotype.Component;


@Component
public class MyReporterFilter implements ReporterFilter {
    @Override
    public void beforeReport(ReportContext context) {

    }

    @Override
    public void afterReport(ReportContext context) {

    }
}
