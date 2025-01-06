package cn.flamingbird.web.controller;

import cn.flamingbird.reporter.Reporter;
import cn.flamingbird.reporter.autoconfigure.ReporterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
//    @Qualifier("errorReporter")
    Reporter reporter;

    @Resource
    List<Reporter> reporters;
    @Resource
    ReporterProperties properties;

    @Resource
    private GenericApplicationContext applicationContext;


    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(properties);
    }
}
