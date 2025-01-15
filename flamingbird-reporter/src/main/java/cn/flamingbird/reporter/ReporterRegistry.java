package cn.flamingbird.reporter;

import lombok.Data;

import java.util.List;

@Data
public class ReporterRegistry {

    private String name;

    private String url;

    private ReporterType type;

    private String color;

    private List<ReporterFilter> reporterFilters;

    public ReporterRegistry() {
        this.color = "red";
        // 目前飞书支持的颜色：https://open.feishu.cn/document/uAjLw4CM/ukzMukzMukzM/feishu-cards/enumerations-for-fields-related-to-color
    }
}
