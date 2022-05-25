# SpringBootOAuth



## 개념 및 용어

- Spring 에서 제공하는 Oauth2 프로젝트를 이용한 OAuth Authorization Server
- `OAuth Authorization` 은 클라이언트가 서비스 제공자로부터 회원 리소스를 제공받기 위한 인증 및 권한 부여를 받는 일련의 절차
- 제휴한 앱에 회원정보 접근을 승인하는 과정을 제공하는 것이 `Authorization`

- `Authorization` 서버의 인증 타입은 총 4가지가 있으며 이 중 `authorization_code` 타입이 가장 대중적임.

![SpringSecurity-oauth2](readme_imgs/SpringSecurity-oauth2.png)
<authorization_code 타입 인증의 대략적인 Flow>

- 위 그림에서 로그인은 Authentication
- 로그인 후 회원의 리소스를 접근할 수 있도록 권한을 부여받는 것을 Authorization + AccessControl 



### Authorization 인증 방식

| 방식 | 설명 |
| ---- | ---- |
| Authorization Code | 가장 대중적인 방식.<br />`Service Provider` 가 제공하는 인증 화면에 로그인 하고<br />클라이언트 앱이 요청하는 리소스 접근 요청을 승인하면, <br />지정한 `redirect_uri`로 `code`를 넘겨주는데 해당 `code`로 `access_token`을 얻음. |
| Implicit | Authorization Code 와 flow 비슷<br />인증 후 `redirect_uri`로 직접 `access_token`을 전달받으므로,<br />전체 프로세스는 좀 더 간단해지지만 `Authorization Code` 방식에 비해 보안성은 떨어짐 |
| password credential | `Resource Owner`가 직접 `Client`에 아이디와 패스워드를 입력하고<br />`Authorization`서버에 해당 정보로 인증받아 `access_token`을 직접 얻어오는 방식.<br />`access token`을 얻어올 때 `Client`에 아이디, 패스워드가 노출되며<br />보안이 떨어지므로 일반적인 공식 애플리케이션에서만 사용. |
| client credential | `access_token`을 얻는데 정해진 인증 `key(secret)`로 요청하며,<br />일반적인 사용 보다는 `server`간 통신을 할 때 사용. |



## jwt

`access_token`은 `bearer` 토큰 형식임. (단순히 암호화된 문자열)
`bearer` 토큰으로 리소스 서버에 리소스를 요청하면 해당 토큰이 유효한지, 인증된 회원인지 확인이 필요함.

`jwt`는 이러한 단점을 보완하기 위해 만들어진 토큰. (`jwt`토큰은 `JSON String`이 암호화된 문자열)
`bearer`토큰과 다르게 토큰 자체에 특정한 정보를 넣을 수 있음. (id, 권한 등 인증에 필요한 정보)

![SpringSecurity-oauth2-jwt](readme_imgs/SpringSecurity-oauth2-jwt.png)



### test 샘플 url

- 일반 테스트

```
http://localhost:8081/oauth/authorize?client_id=testClientId&redirect_uri=http://localhost:8081/oauth2/callback&response_type=code&scope=read
```

- refresh token 테스트

```
http://localhost:8081/oauth2/token/refresh?refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJoYXBweWRhZGR5QGdtYWlsLmNvbSIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiOTkxMDExNjktZmVjYS00NWUxLTliZTgtYmZjYzZiYjM0Yjg4IiwiZXhwIjoxNTU2MTQ1NDUyLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiODZhMDliYjctMzA2NS00NGQ5LWIxYjEtNWJlOGFlMjdlOWZkIiwiY2xpZW50X2lkIjoidGVzdENsaWVudElkIn0.fL51VTaLMHEf89Ao41nUrW-MzaNGBupg_4Us5nEtL_4
```

### 참고
- [https://daddyprogrammer.org/post/series/spring-boot-oauth2/](https://daddyprogrammer.org/post/series/spring-boot-oauth2/)
