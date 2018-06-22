package groovy

import spock.lang.*

class AdderSpec extends Specification{
    def "adder-test"() {
        given: "a new Adder class is created"
        def result = 7;

        expect: "Adding two numbers to return the sum"
        result == 7
    }
}
