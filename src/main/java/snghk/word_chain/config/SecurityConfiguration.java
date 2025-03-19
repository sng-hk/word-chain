package snghk.word_chain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import snghk.word_chain.handler.CustomAuthenticationFailureHandler;
import snghk.word_chain.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/", "/home", "/customLogin", "/signup"
                                        , "/css/**", "/js/**", "/images/**"
                                        , "/contact"
                                        , "/error").permitAll()
                                .anyRequest().authenticated())
                .formLogin(formLongin -> formLongin
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/customLogin")
                        .loginProcessingUrl("/customLogin") // 로그인 처리 URL 설정
                        .defaultSuccessUrl("/")
                        .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 동작
                        .failureHandler(customAuthenticationFailureHandler)) // 로그인 실패 시 동작)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/customLogin")
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")  // 세션 쿠키 삭제
                        .permitAll());  // 로그아웃 페이지는 누구나 접근 가능);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // BCryptPasswordEncoder
    }
}
