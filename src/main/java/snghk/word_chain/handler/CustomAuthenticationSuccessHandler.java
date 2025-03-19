package snghk.word_chain.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 후 이동할 URL
        // 이메일과 비밀번호를 request에서 추출
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.trace("로그인 시도 - 이메일: " + email);  // 비밀번호는 출력하지 마세요
        log.trace("로그인 시도 - 비밀번호: " + password);
        response.sendRedirect("/home"); // 예시로 /home으로 리디렉션
    }
}

