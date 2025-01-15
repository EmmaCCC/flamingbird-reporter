package cn.flamingbird.web.controller;

import cn.flamingbird.reporter.ReportContext;
import cn.flamingbird.reporter.ReporterInterceptor;
import cn.flamingbird.reporter.ReporterRegistry;
import cn.flamingbird.reporter.ReporterType;
import org.springframework.stereotype.Component;


@Component
public class DingdingReporterInterceptor implements ReporterInterceptor {
    @Override
    public boolean canUse(ReporterRegistry registry) {
        return registry.getType() == ReporterType.DINGDING;
    }

    @Override
    public void beforeReport(ReportContext context) {

    }

    @Override
    public void afterReport(ReportContext context) {

    }
}
