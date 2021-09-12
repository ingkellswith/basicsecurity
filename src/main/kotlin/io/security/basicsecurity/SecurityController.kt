package io.security.basicsecurity

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController {

    @GetMapping("/")
    fun index(): String{
        return "home"
    }

    @GetMapping("/login-page")
    fun loginPage(): String{
        return "loginPage"
    }

    @GetMapping("/test")
    fun test(): String{
        return "testing"
    }
}