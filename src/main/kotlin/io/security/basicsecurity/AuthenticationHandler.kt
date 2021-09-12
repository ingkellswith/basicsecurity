package io.security.basicsecurity

import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationHandler(){
    class CustomAuthenticationSuccessHandler: AuthenticationSuccessHandler {
        override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
            println("authentication" + authentication?.name)
            response?.sendRedirect("/")
        }
    }

    class CustomAuthenticationFailureHandler: AuthenticationFailureHandler {
        override fun onAuthenticationFailure(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            exception: AuthenticationException?
        ) {
            println("exception" + exception?.message)
            response?.sendRedirect("/login")
        }
    }
}