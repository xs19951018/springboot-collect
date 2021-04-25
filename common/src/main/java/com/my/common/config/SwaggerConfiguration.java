package com.my.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
//@Profile({"local","daily","dev"})
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("user token，用于验证某些接口是否有权限")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        ParameterBuilder ticketPar_ = new ParameterBuilder();
        ticketPar_.name("jxk_client").description("1：IOS端；2：安卓端；3.小程序；;4.1 芝麻鲸选后台管理；")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();

        ParameterBuilder appVersion = new ParameterBuilder();
        appVersion.name("app_version").description("版本")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        pars.add(ticketPar.build());
        pars.add(ticketPar_.build());
        pars.add(appVersion.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xmartmonkey.service.mengshopservice"))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("首页").description("接口文档").version("1.0.0-SNAPSHOT").termsOfServiceUrl("http://mengshop.xmartmonkey.com").build();
    }
}
