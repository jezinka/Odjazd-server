package com.jezinka.odjazd.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OtherController {

    @RequestMapping("/groovy")
    String home() {
        return "Hello World!"
    }
}