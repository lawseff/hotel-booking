package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // show_home_page
    // change_language
    // update_page

    @GetMapping({ "/", "/home" })
    public String getHomePage() {
        return "index";
    }

}
