package io.security.basicsecurity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.RememberMeServices

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService
    // loginPage는 접근 권한이 필요한 페이지로 갈 때 로그인이 되어 있지 않으면 redirect되는 페이지
    // 아래는 모든 페이지에 접근 권한이 필요하므로 어떤 페이지로 가든 /login-page로 간다.
    // defaultSuccessUrl는 default로 successHandler에서 설정 가능
    // failureUrl는 default로 failureHandler에서 설정 가능
    // usernameParameter, passwordParameter는 input의 "name"으로 스프링 시큐리티에서는 이 네임을 기준으로 폼로그인을 진행한다.
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
            //.loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/login")
            .usernameParameter("userId")
            .passwordParameter("passwd")
            //.loginProcessingUrl("/login_proc")
            .successHandler(AuthenticationHandler.CustomAuthenticationSuccessHandler())
            .failureHandler(AuthenticationHandler.CustomAuthenticationFailureHandler())
            // 로그인 페이지는 허용되어야하므로 permitAll()
            .permitAll()
        http
            .logout()
            // 이 url로 진입 시 로그아웃 가능
            .logoutUrl("/logout")
            // logout 성공 시 redirect되는 url
            .logoutSuccessUrl("/login")
            // logout 진행할 시
            .addLogoutHandler(LogOutHandler.CustomLogOutHandler())
            // logout 성공 시
            .logoutSuccessHandler(LogOutHandler.CustomLogOutSuccessHandler())
            .deleteCookies("remember-me")
        http
            .rememberMe()
            .rememberMeParameter("remember")
            // 3600초 = 1시간, 기본 14일
            .tokenValiditySeconds(3600)
            .userDetailsService(userDetailsService)
        http
            .sessionManagement()
            .maximumSessions(1)
            // maxSessionsPreventsLogin의 param이 true이면 새로 로그인하는 사용자가 로그인할 수 없음
            // false이면 이전 세션을 종료 처리
            .maxSessionsPreventsLogin(false)
        http
            .sessionManagement()
            .sessionFixation()
            // changeSessionId()는 기본값으로 설정이 필요 없음
            .changeSessionId()
    }
}