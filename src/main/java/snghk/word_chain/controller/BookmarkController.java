package snghk.word_chain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {
    @GetMapping("")
    public String bookmark() {
        return "bookmark";
    }
}
