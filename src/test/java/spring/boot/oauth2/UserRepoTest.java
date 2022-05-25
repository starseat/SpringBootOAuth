package spring.boot.oauth2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import spring.boot.oauth2.entity.User;
import spring.boot.oauth2.repository.UserJpaRepository;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserJpaRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertNewTestUser() {
        userRepo.save(
                User.builder()
                        .uid("test001@test.co.kr")
                        .password(passwordEncoder.encode("1234"))
                        .name("test001")
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );
    }
}
