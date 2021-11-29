package web.controller;

import web.utils.CurrentPageGetter;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PageController {

    private static final String LOCALE_PARAMETER = "locale";
    private static final String LOCALE_ATTRIBUTE = "locale";

    @GetMapping({ "/", "/home" })
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/change_language")
    public RedirectView changeLanguage(HttpServletRequest request) {
        String localeParameter = request.getParameter(LOCALE_PARAMETER);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
        String page = CurrentPageGetter.getCurrentPage(request);
        return new RedirectView(page);
    }

    @GetMapping("/update_page")
    public RedirectView updatePage(HttpServletRequest request) {
        String page = CurrentPageGetter.getCurrentPage(request);
        return new RedirectView(page);
    }

}
