package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_database_tg";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    }

    public static PreparedStatement createPreparedStateMent(String query,
                                                     Connection connection,
                                                     Object[] params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i = 0; i < params.length; i++){
            statement.setObject(i+1,params[i]);
        }
        return statement;
    }
}
