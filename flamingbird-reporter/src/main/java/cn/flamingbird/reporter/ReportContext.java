package cn.flamingbird.reporter;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Data
public class ReportContext {

    private String summary;

    private String content;

    private Map<String, String> map;

    private Throwable throwable;

    private HttpServletRequest request;

    private Object reportRequest;

    private String reportResponse;

    private Exception exception;

    private Reporter reporter;
}
