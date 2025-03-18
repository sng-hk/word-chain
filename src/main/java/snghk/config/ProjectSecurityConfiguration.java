package snghk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity public class ProjectSecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 세션 설정
            .sessionManagement(sessionConfig ->
                    sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

            // 인증 및 권한 설정
            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers().permitAll()
//                .requestMatchers("/home").permitAll() // 보안 URL
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )

            // cors 설정
            // CORS 비활성화

            // csrf 설정
//            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            // 로그인 방식 설정
            .formLogin(form -> form
                            .defaultSuccessUrl("/")
                    .failureUrl("/login")) // 기본 로그인 폼 사용
            .httpBasic(withDefaults()); // HTTP Basic 인증 사용
        return http.build(); // 필터 체인 반환
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
