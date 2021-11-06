package demo.wsc.beta.controller.authenticate

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Home {

    @GetMapping("/")
    fun home():String{
        return "----------------------------Customer Service-----------------------------------"
    }
}