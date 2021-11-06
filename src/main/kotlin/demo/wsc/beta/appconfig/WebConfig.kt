/*;==========================================
; Title:  Cors origin web configuration for server
; Author: Rupak Kumar
; Date:   5 Oct 2021
;==========================================*/

package demo.wsc.beta.appconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    @Autowired
    lateinit var appProperties: AppProperties

    /**
     * Configuration for cros Origin
     *
     * @param 'NA'
     * @return - 'NA'
     */

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(appProperties.urlClient)
            .allowedMethods("*")
    }
}
