package io.security.basicsecurity

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    // loginPage는 접근 권한이 필요한 페이지로 갈 때 로그인이 되어 있지 않으면 redirect되는 페이지
    // 아래는 모든 페이지에 접근 권한이 필요하므로 어떤 페이지로 가든 /login-page로 간다.
    // defaultSuccessUrl는 default로 successHandler에서 설정 가능
    // failureUrl는 default로 failureHandler에서 설정 가능
    // usernameParameter, passwordParameter는 form의 name으로 스프링 시큐리티에서는 이 네임을 기준으로 폼로그인을 진행한다.
    // 예로, html element console에서 user input의 name을 userId가 아닌 다른 name으로 바꾸면 키 값이 달라 인증이 실패한다.
    // loginProcessingUrl >> form의 action으로 react의 onSubmit과 같다.
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/test")
            .permitAll()
            // .antMatchers("/test").permitAll()로 인해 /test는 로그인되지않아도 접근가능
            .anyRequest()
            .authenticated()
        http
            .formLogin()
            .loginPage("/login-page")
            .defaultSuccessUrl("/")
            .failureUrl("/login")
            .usernameParameter("userId")
            .passwordParameter("passwd")
            .loginProcessingUrl("/login_proc")
            .successHandler(AuthenticationHandler.CustomAuthenticationSuccessHandler())
            .failureHandler(AuthenticationHandler.CustomAuthenticationFailureHandler())
            // 로그인 페이지는 허용되어야하므로 permitAll()
            .permitAll()
    }
}