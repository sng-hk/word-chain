package snghk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity public class ProjectSecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            // 인증 및 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/customLogin", "/signup", "/contact", "/error", "/").permitAll() // 로그인과 회원가입은 인증 없이 접근 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )
            // 세션 설정
            .sessionManagement(sessionConfig ->
                    sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            // cors 설정
            // CORS 비활성화

            // csrf 설정
//            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            // 로그인 방식 설정
            .formLogin(form -> form
                    .loginPage("/customLogin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                            .defaultSuccessUrl("/user-info", true)
                    .failureUrl("/customLogin")); // 커스텀 로그인
        return http.build(); // 필터 체인 반환
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
