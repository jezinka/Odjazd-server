package com.jezinka.odjazd.controller

import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class OtherControllerTest extends spock.lang.Specification {

    @Shared
    MockMvc mockMvc = standaloneSetup(new OtherController()).build()

    def "Home"() {
        when: 'rest account url is hit'
        def response = mockMvc.perform(get('/groovy')).andReturn().response

        then:

        //Testing the HTTP Status code
        response.status == OK.value()

        //Showing how a contains test could work
        response.contentType.contains('text/plain')
        response.contentType == 'text/plain;charset=ISO-8859-1'

        //Can test the whole content string that is returned
        response.contentAsString == 'Hello World!'

    }
}
