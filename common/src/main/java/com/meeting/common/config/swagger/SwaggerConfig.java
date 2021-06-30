package com.meeting.common.config.swagger;

import com.meeting.common.constant.CommonConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

/**
 * @dateTime 2019-03-28 20:16
 * @author: dameizi
 * @description: Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("dev")
    private String profile;

    @Bean
    public Docket createRestApi() {
        //线上环境
        if("prod".equals(this.profile)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(prodApiInfo())
                    .select()
                    //如果是线上环境，添加路径过滤，设置为全部都不符合
                    .paths(PathSelectors.none())
                    .build();
        } else {
            ParameterBuilder token = new ParameterBuilder();
            List<Parameter> parameter = new ArrayList<Parameter>();
            token.name(CommonConst.AUTHORIZATION).description(CommonConst.AUTHORIZATION)
                    .modelRef(new ModelRef("string")).parameterType("header")
                    //header中的ticket参数非必填，传空也可以
                    .required(false).build();
            //根据每个方法名也知道当前方法在设置什么参数
            parameter.add(token.build());

            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .globalOperationParameters(parameter)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com."))
                    .paths(PathSelectors.any())
                    .build();
        }

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Project RESTful APIs")
                .description("Project RESTful APIs")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("https://swagger.io/")
                .version("1.0.1")
                .build();
    }

    private ApiInfo prodApiInfo() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .build();
    }

}
