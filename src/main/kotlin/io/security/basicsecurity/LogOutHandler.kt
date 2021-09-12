package io.security.basicsecurity

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

open class LogOutHandler {
    class CustomLogOutHandler: LogoutHandler {
        override fun logout(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ){
            val session = request?.getSession()
            session?.invalidate()
        }
    }
    class CustomLogOutSuccessHandler: LogoutSuccessHandler{
        override fun onLogoutSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
            response?.sendRedirect("/login")
        }
    }
}