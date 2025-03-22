package snghk.word_chain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import snghk.word_chain.config.jwt.JwtTokenProvider;
import snghk.word_chain.domain.LoginRequest;
import snghk.word_chain.domain.users.domain.Users;
import snghk.word_chain.repository.UsersRepository;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/jwtLogin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 인증 (이메일과 비밀번호로 사용자 확인)
        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 비밀번호 검증 (예: BCryptPasswordEncoder 사용)
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());

        // JWT 토큰을 클라이언트에게 반환
        return ResponseEntity.ok(token);
    }
}
