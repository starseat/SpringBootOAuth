package spring.boot.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.boot.oauth2.entity.User;
import spring.boot.oauth2.repository.UserJpaRepository;

/**
 * 로그인 정보의 유효성 검증 용
 *  - 회원 이름으로 DB 조회하여 회원정보의 비밀번호와 입력된 비밀번호의 매칭 여부 확인
 *  - 회원이 없거나 비밀번호가 틀린 경우 Exception 발생
 *  - 로그인 정보가 유효한 경우 회원 정보와 권한 정보로 인증 정보 생성하여 리턴
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepo;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepo.findByUid(name).orElseThrow(
                () -> new UsernameNotFoundException("user is not exists.")
        );

        if ( !passwordEncoder.matches(password, user.getPassword()) ) {
            throw new BadCredentialsException("password is not valid.");
        }

        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
