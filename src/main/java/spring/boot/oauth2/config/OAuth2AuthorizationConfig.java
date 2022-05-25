package spring.boot.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import spring.boot.oauth2.service.security.CustomUserDetailService;

import javax.sql.DataSource;

/**
 * id/password 기반 Oauth2 인증을 담당하는 서버
 * 다음 endpont가 자동 생성 된다.
 * /oauth/authorize
 * /oauth/token
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.jwt.signkey}")
    private String signKey;

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    private final CustomUserDetailService userDetailService;

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("testClientId")
//                .secret("testSecret")
//
//                // 인증 완료 후 이동할 클라이언트 웹 페이지 주소로 code 값을 실어서 보내줌.
//                .redirectUris("http://localhost:8081/oauth2/callback")
//
//                // 인증 방식중 authorization_code 사용
//                .authorizedGrantTypes("authorization_code")
//
//                // 인증 후 얻은 access token으로 접근할 수 있는 리소스 범위
//                .scopes("read", "write")
//
//                // 발급된 access token 의 유효시간 (초)
//                .accessTokenValiditySeconds(30000);  // 5분

        // client 정보 주입 방식을 jdbcdetail 로 변경
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

//    // 토큰 처리시 DB 사용 / 토큰 정보를 DB 로 관리
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource));
//    }

    // 토큰 발급 방식을 JWT 토큰 방식으로 적용
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws  Exception {
        super.configure(endpoints);
        endpoints
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailService)
        ;
    }

    // jwt converter 등록
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        return new JwtAccessTokenConverter();

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signKey);
        return converter;
    }
}
