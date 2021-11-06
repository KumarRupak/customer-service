package demo.wsc.beta


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.scheduling.annotation.EnableScheduling



@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
open class WebSmartCreditApplication

	fun main(args: Array<String>) {
		runApplication<WebSmartCreditApplication>(*args)
	}

