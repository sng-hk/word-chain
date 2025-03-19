package snghk.word_chain.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import snghk.word_chain.domain.users.domain.Users;
import snghk.word_chain.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(null!=username) {
            log.trace("email is null");
        }
        log.trace("로그인 시도 - 이메일: " + username);
        Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.trace("로그인 시도 - 비밀번호: " + user.getPassword());
        return new User(
                user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole()));
    }
}
