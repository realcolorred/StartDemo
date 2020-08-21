package com.example.pubserv.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by new on 2020/8/13.
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger.enable:false}")
    private boolean enable;
    @Value("${swagger.basePackage:}")
    private String  basePackage;
    @Value("${swagger.title:}")
    private String  title;
    @Value("${swagger.description:}")
    private String  description;
    @Value("${swagger.version:}")
    private String  version;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return 1
     */
    @Bean
    public Docket createRestApi() {
        //访问地址：http://项目实际地址/swagger-ui.html
        //版本类型是swagger2
        return new Docket(DocumentationType.SWAGGER_2).enable(enable)
            //通过调用自定义方法apiInfo，获得文档的主要信息
            .apiInfo(new ApiInfoBuilder() //
                         .title(title) //接口管理文档首页显示
                         .description(description)//API的描述
                         .version(version) //
                         .build() //
                    ).select() //
            .apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()) //扫描该包下面的API注解
            .build();
    }
}
