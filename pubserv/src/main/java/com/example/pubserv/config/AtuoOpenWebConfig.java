package com.example.pubserv.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by new on 2020/8/21.
 *
 * 启动成功自动打开页面，开发专用，默认关闭。
 */
@Component
public class AtuoOpenWebConfig implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(AtuoOpenWebConfig.class);

    @Value("${spring.auto.isOpen:false}")
    private boolean isOpen;

    @Value("${server.host:127.0.0.1}")
    private String host;

    @Value("${server.port:8080}")
    private String port;

    @Value("${spring.auto.openPage:/}")
    private String openPage;

    @Override
    public void run(String... strings) throws Exception {
        if (isOpen) {
            String url = "http://" + host + ":" + port + openPage;
            Runtime run = Runtime.getRuntime();
            try {
                run.exec("cmd /c start " + url);
                logger.info("=========================== 项目启动成功自动打开网址{} =================================", url);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}
