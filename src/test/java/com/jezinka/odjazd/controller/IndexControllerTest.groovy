package com.jezinka.odjazd.controller

import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class IndexControllerTest extends spock.lang.Specification {

    @Shared
    MockMvc mockMvc = standaloneSetup(new IndexController()).build()

    def "Home"() {
        when: 'rest account url is hit'
        def response = mockMvc.perform(get('/')).andReturn().response

        then:

        //Testing the HTTP Status code
        response.status == OK.value()

        //Showing how a contains test could work
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'
    }
}
