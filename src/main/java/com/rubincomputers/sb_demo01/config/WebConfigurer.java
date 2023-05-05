package com.rubincomputers.sb_demo01.config;

import com.rubincomputers.sb_demo01.config.util.h2.H2ConfigurationHelper;
import com.rubincomputers.sb_demo01.config.util.h2.H2DataSourceFileCreation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Slf4j
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {


    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;


    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
        //setLocationForStaticAssets(server);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        if (env.acceptsProfiles(Profiles.of(SpringProfiles.DEV))) {
            initH2Console(servletContext);
        }
        log.info("Web application fully configured");
    }



    /**
     * Initializes H2 console.
     */
    private void initH2Console(ServletContext servletContext) {
        log.debug("Initialize H2 console");
        H2DataSourceFileCreation.createPropertiesFile("target/h2db/.h2.server.properties", dataSource);
        H2ConfigurationHelper.initH2Console(servletContext);
    }
}
