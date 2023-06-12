package dao.entitiesVO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public abstract class BaseDAO {
    protected static Connection connection = null;

    protected static Connection getConnection() {
        if (connection == null) {
            Properties properties = getProperties();

            try {
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties.getProperty("user"),
                        properties.getProperty("password"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return connection;
        }

        return connection;
    }

    public static void fecharConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void fecharPreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void fecharResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Properties getProperties() {
        String path = "C:\\Users\\gabri\\OneDrive\\Documentos\\Conteúdo Faculdade\\Segundo Semestre\\" +
                "Técnicas de programação\\trabalhos\\trabalho4\\src\\files\\db.properties.txt";

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
