package cn.flamingbird.reporter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;


public class FeishuReporter extends AbstractReporter {

    private final String name;
    private final String url;


    public FeishuReporter(ReporterRegistry registry) {
        super(registry);
        this.name = registry.getName();
        this.url = registry.getUrl();
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ReporterType getReporterType() {
        return ReporterType.FEISHU;
    }


    @Override
    protected void prepareReport(ReportContext reportContext) {

        String summary = reportContext.getSummary();
        String content = reportContext.getContent();
        Map<String, String> map = reportContext.getMap();

        if (map != null && map.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append("**").append(entry.getKey()).append("**：").append(entry.getValue()).append("\n");
            }
            if (content == null) {
                content = stringBuilder.toString();
            } else {
                content = content + "\n" + stringBuilder;
            }
        }
        content = "<font color='" + registry.getColor() + "'>" + content + "</font><at id=all></at>";

        HashMap<String, Object> card = new HashMap<>();

        HashMap<String, Object> header = new HashMap<>();
        HashMap<String, String> title = new HashMap<>();
        title.put("tag", "plain_text");
        title.put("content", summary);
        header.put("template", registry.getColor());
        header.put("title", title);


        HashMap<String, Object> elements = new HashMap<>();
        HashMap<String, String> text = new HashMap<>();
        text.put("content", content);
        text.put("tag", "lark_md");
        elements.put("tag", "div");
        elements.put("text", text);

        card.put("header", header);
        card.put("elements", new Object[]{elements});

        HashMap<String, Object> msg = new HashMap<>();
        msg.put("msg_type", "interactive");
        msg.put("card", card);

        reportContext.setReportRequest(msg);

    }

    @Override
    protected void executeReport(ReportContext reportContext) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");

        String body = JSONUtil.toJsonStr(reportContext.getReportRequest());
        try (HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers).body(body, "application/json").execute()) {
            String res = response.body();
            reportContext.setReportResponse(res);
        }
    }


}
