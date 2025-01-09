package cn.flamingbird.reporter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;


public class FeishuReporter implements Reporter {

    private final String name;
    private final String url;


    public FeishuReporter(ReporterRegistry registry) {
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
    public void report(String summary, Map<String, String> map) {
        String content = summary;
        if (map != null && map.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append("**").append(entry.getKey()).append("**：").append(entry.getValue()).append("\n");
            }
            content = stringBuilder.toString();
        }
        report(summary, content);
    }

    public void report(String summary, String content) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");

        HashMap<String, Object> card = new HashMap<>();

        HashMap<String, Object> header = new HashMap<>();
        HashMap<String, String> title = new HashMap<>();
        title.put("tag", "plain_text");
        title.put("content", summary);
        header.put("template", "red");
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
        String body = JSONUtil.toJsonStr(msg);
        try (HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers).body(body, "application/json").execute()) {
            String res = response.body();
        }
    }


}
