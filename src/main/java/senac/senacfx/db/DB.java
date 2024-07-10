package senac.senacfx.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    private DB() {
        // Construtor privado para evitar instâncias externas
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = "jdbc:mysql://localhost:3306/senacprojeto";
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                // Propaga a exceção original
                throw new RuntimeException("Erro ao obter conexão com o banco de dados.", e);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar conexão com o banco de dados.", e);
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar as propriedades do banco de dados.", e);
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar o Statement.", e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar o ResultSet.", e);
            }
        }
    }
}