package spring.boot.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    //    // 암호화 준비가 안되어 있어 임시 사용 / 새로운 passwordEncoder 는 WebMvcConfig 로 이동
//    @Bean
//    public PasswordEncoder noOpPasswordEncoder() {
//      return NoOpPasswordEncoder.getInstance();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("pass")
//                .roles("USER");

        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                // security 적용시 h2 console 사용이 막히므로 이를 방지하기 위하여 추가
                .headers().frameOptions().disable()
                .and()
                // "/oauth/**" => authorization 서버 세팅시 자동으로 생성되는 주소를 누구나 접근할 수 있게 하기 위한 세팅
                // "/oauth2/callback", "/h2-console/*" => callback 테스트 용도
                //.authorizeRequests().antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
                .authorizeRequests().antMatchers("/oauth/**", "/oauth/token", "/oauth2/**").permitAll() // mariadb 사용하므로 h2-console 제거
                .and()
                // security 기본 폼 사용
                .formLogin().and()
                .httpBasic();
    }
}
