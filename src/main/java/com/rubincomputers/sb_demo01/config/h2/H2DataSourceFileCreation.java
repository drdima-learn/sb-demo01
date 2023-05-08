package com.rubincomputers.sb_demo01.config.h2;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class H2DataSourceFileCreation {
    public static void createPropertiesFile(String fileName, DataSource dataSource) {
        try {
            // Create a new file object
            File file = new File(fileName);
            file.getParentFile().mkdirs();

            // Create a new BufferedWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));


            String jdbcUrl = getJdbcUrlFromDataSource(dataSource);
            String userName = getUserNameFromDataSource(dataSource);
            //String password = getPasswordFromDataSource(dataSource);
            String password = null;
            String driverClassName = getDriverClassNameFromDataSource(dataSource);


            // Write some text to the file
            String text = "#H2 Server Properties\n" +
                    "0=RubinComputers H2 (Disk)|" + driverClassName + "|" + jdbcUrl + "|" + userName + (password != null ? "|" + password : "") + "\n" +
                    "webSSL=false\n" +
                    "webAllowOthers=true\n" +
                    "webPort=8092";
            writer.write(text);

            // Close the writer
            writer.close();

            log.debug("H2 properties {} file created successfully.", fileName);

        } catch (IOException e) {
            log.warn("Error creating file: {}" , e.getMessage());
        }
    }


    private static HikariDataSource getHikariDataSource(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            return hikariDataSource;
        }
        throw new RuntimeException("no hikari datasource");
    }

    private static String getPasswordFromDataSource(DataSource dataSource) {
        return getHikariDataSource(dataSource).getPassword();

    }

    private static String getUserNameFromDataSource(DataSource dataSource) {
        return getHikariDataSource(dataSource).getUsername();
    }

    private static String getJdbcUrlFromDataSource(DataSource dataSource) {
        return getHikariDataSource(dataSource).getJdbcUrl();
    }

    private static String getDriverClassNameFromDataSource(DataSource dataSource) {
        return getHikariDataSource(dataSource).getDriverClassName();
    }
}
