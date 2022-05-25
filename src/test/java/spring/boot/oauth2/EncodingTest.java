package spring.boot.oauth2;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodingTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.printf("testSecret: %s\n", passwordEncoder.encode("testSecret"));
        System.out.printf("testSecret - 1234: %s\n", passwordEncoder.encode("1234"));
    }
}