package demo.wsc.beta.appconfig

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@Component
class RestClient {

    @Bean
    @LoadBalanced
    fun getRestTamplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    @LoadBalanced
    fun getWebClientBuilder(): WebClient.Builder{
        return WebClient.builder()
    }
}