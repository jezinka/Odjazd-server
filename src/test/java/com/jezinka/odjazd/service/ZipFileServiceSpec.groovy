package com.jezinka.odjazd.service

import spock.lang.Specification

import java.time.LocalTime

class ZipFileServiceSpec extends Specification {

    def "findRoute -> test"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        zipFileService.findRoute()

        then:
        noExceptionThrown()
    }

    def "isInInterval"() {
        given:
        ZipFileService zipFileService = new ZipFileService()

        when:
        boolean result = zipFileService.isInInterval(departureTime)

        then:
        println departureTime
        result == expectedResult

        where:
        departureTime            | expectedResult
        LocalTime.parse("13:00") | false
        LocalTime.parse("14:45") | true
        LocalTime.parse("15:10") | true
        LocalTime.parse("16:00") | false

    }
}
