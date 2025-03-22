package snghk.word_chain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import snghk.word_chain.domain.users.domain.Users;
import snghk.word_chain.domain.users.dto.UsersDto;
import snghk.word_chain.repository.UsersRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"/customLogin", "/jwtLogin"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UsersDto()); // 회원가입 폼에 전달할 객체
        return "signup"; // signup.html로 이동
    }

    @PostMapping("/signup")
    public String signup(UsersDto usersDto) {
        // UsersDto를 Users 엔티티로 변환
        Users user = new Users();
        user.setEmail(usersDto.getEmail());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword())); // 비밀번호는 보통 암호화해서 저장해야 합니다
        user.setRole("USER");
        user.setEmail(usersDto.getEmail());

        // DB에 저장
        usersRepository.save(user);

        // 가입 성공 후 로그인 페이지로 리디렉션
        return "redirect:/customLogin"; // 리디렉션
    }

    // 사용자 정보 페이지
    @GetMapping("/user-info")
    public String userInfo(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 로그인한 사용자 정보 가져오기
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            model.addAttribute("user", user);  // 사용자 정보를 모델에 추가
        }
        return "user-info";  // user-info.html로 이동
    }
}