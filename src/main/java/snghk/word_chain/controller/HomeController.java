package snghk.word_chain.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 있다면 이름을 모델에 추가
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이름
            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "anonymous user");
        }

        return "home"; // home.html로 이동
    }
}
