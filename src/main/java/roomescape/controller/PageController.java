package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String adminMainPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationMainPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String timeMainPage() {
        return "time";
    }
}
