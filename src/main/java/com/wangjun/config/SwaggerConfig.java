package com.wangjun.config;

import com.wangjun.Application;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wangjun
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ObjectProvider<BuildProperties> buildProperties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(Application.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getHeaders());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description("王俊")
                .version(Optional.ofNullable(buildProperties.getIfAvailable())
                        .map(BuildProperties::getVersion)
                        .orElse("1.0.0"))
                .build();
    }

    private List<Parameter> getHeaders() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Authorization").description("登录token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        pars.add(ticketPar.build());
        return pars;
    }

}
