package snghk.word_chain.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        org.springframework.security.core.AuthenticationException exception)
            throws IOException, ServletException {
        // 이메일과 비밀번호를 request에서 추출
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.trace("로그인 시도 - 이메일: " + email);  // 비밀번호는 출력하지 마세요
        log.trace("로그인 시도 - 비밀번호: " + password);
        if (exception instanceof BadCredentialsException) {
            request.setAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        // 실패 시 로그인 페이지로 다시 리디렉션
        response.sendRedirect("/customLogin?error=true");
    }
}
