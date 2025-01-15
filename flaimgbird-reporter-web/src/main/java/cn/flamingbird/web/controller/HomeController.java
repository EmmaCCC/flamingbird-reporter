package cn.flamingbird.web.controller;

import cn.flamingbird.reporter.Reporter;
import cn.flamingbird.reporter.autoconfigure.ReporterManager;
import cn.flamingbird.reporter.autoconfigure.ReporterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HomeController {

    @Resource
    Reporter reporterMyFeishu;

    @Autowired
    List<Reporter> reporters;

    @Autowired
    Reporter reporter;

    @Autowired
    ReporterManager reporterManager;
    @Resource
    ReporterProperties properties;

    @Resource
    private GenericApplicationContext applicationContext;


    @PostMapping("/index")
    public ResponseEntity<?> index(@RequestBody Person person, HttpServletRequest request) {
        reporterMyFeishu.report(request);
        return ResponseEntity.ok(person);
    }

}
