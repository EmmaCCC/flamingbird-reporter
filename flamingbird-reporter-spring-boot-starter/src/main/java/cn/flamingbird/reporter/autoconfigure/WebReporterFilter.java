package cn.flamingbird.reporter.autoconfigure;

import cn.flamingbird.reporter.ReportContext;
import cn.flamingbird.reporter.ReportItems;
import cn.flamingbird.reporter.ReporterFilter;
import cn.flamingbird.reporter.utils.HttpServletUtils;
import cn.flamingbird.utils.HostEnvironment;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebReporterFilter implements ReporterFilter {

    private final HostEnvironment hostEnvironment;

    public WebReporterFilter(HostEnvironment hostEnvironment) {
        this.hostEnvironment = hostEnvironment;
    }

    @Override
    public void beforeReport(ReportContext context) {
        Map<String, String> map = context.getMap();

        String applicationName = hostEnvironment.getApplicationName();
        String profile = hostEnvironment.getActiveProfile();
        String hostName = hostEnvironment.getHostName();

        map.put(ReportItems.APPLICATION_NAME, applicationName);
        map.put(ReportItems.ACTIVE_PROFILE, profile);
        map.put(ReportItems.HOST_NAME, hostName);

        HttpServletRequest request = context.getRequest();
        if (request != null) {
            String url = request.getRequestURL().toString();
            String body = HttpServletUtils.readRequestBody(context.getRequest());

            context.setSummary(url);
            map.put(ReportItems.REQUEST_URL, url);
            map.put(ReportItems.REQUEST_BODY, body);
            map.put(ReportItems.REQUEST_HEADERS, JSONUtil.toJsonStr(ServletUtil.getHeaderMap(request)));
        }

        Throwable throwable = context.getThrowable();
        if (throwable != null) {
            context.setSummary(throwable.getMessage());
            map.put(ReportItems.EXCEPTION_STACK, ExceptionUtil.stacktraceToString(throwable, 30000));
        }

    }

    @Override
    public void afterReport(ReportContext context) {

    }

}
