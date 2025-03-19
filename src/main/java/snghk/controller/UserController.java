package snghk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import snghk.domain.users.domain.Users;
import snghk.domain.users.dto.UsersDto;
import snghk.repository.UsersRepository;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/customLogin")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user-info")
    public String getUserInfo() {
        // SecurityContext에서 인증된 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return "Authenticated user email: " + userDetails.getUsername();  // 이메일 정보 반환
        } else {
            return "No authenticated user";
        }
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UsersDto()); // 회원가입 폼에 전달할 객체
        return "signup"; // signup.html로 이동
    }

    @PostMapping("/signup")
    public String registerUser(UsersDto userDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        // User 객체로 변환하여 저장
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");  // 기본 역할은 일반 사용자로 설정
        user.setCreatedDate(LocalDate.now());

        usersRepository.save(user);

        return "redirect:/login";  // 회원가입 후 로그인 페이지로 리다이렉트
    }
}