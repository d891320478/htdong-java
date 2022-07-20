package com.htdong.common.db.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import lombok.Setter;

@Setter
public class DbInit {

    private DataSource dataSource;

    public void init() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement stat = connection.createStatement()) {
                executeSql(stat, new ClassPathResource("db/init_table.sql"));
            }
        }
    }

    private void executeSql(Statement stat, ClassPathResource sqlFile) throws IOException, SQLException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(sqlFile.getInputStream()))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        String[] s = sb.toString().split(";\n");
        for (String i : s) {
            if (StringUtils.isNotBlank(i)) {
                stat.addBatch(i);
            }
        }
        stat.executeBatch();
        stat.clearBatch();
    }
}