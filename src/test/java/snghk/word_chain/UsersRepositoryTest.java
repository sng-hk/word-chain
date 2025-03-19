package snghk.word_chain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import snghk.word_chain.domain.users.domain.Users;
import snghk.word_chain.repository.UsersRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 외부 DB 사용
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByEmail() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("USER");
        usersRepository.save(user);

        Optional<Users> foundUser = usersRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());  // 이메일로 사용자 찾기
    }
}

