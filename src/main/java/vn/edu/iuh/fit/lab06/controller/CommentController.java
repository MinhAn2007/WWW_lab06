package vn.edu.iuh.fit.lab06.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @GetMapping("/")
    public String index(){
        return "";
    }
}
