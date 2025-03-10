package com.narvasoft.agenda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*Esta clase de Java permite que SpringBoot pueda servir archivos staticoss
*fue necesario incluir este archivo para que las imaégnes de los Pcs s emotrarán correctamente.
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/main.css")
                .addResourceLocations("classpath:/static/");
    }
}
