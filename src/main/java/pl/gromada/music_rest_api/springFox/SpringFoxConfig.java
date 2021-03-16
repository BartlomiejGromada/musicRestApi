package pl.gromada.music_rest_api.springFox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(creatApiInfo());
    }

    private ApiInfo creatApiInfo() {
        return new ApiInfo("Music API",
                "Information about songs and singers",
                "1.00",
                "",
                new Contact("Bartłomiej", "", "bartek@wp.pl"),
                "my own licence",
                "",
                Collections.emptyList());
    }
}
