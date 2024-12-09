import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mercado";
    private static final String USER = "root";
    private static final String PASSWORD = "TiAGO.2006";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();

        }
        return connection;
    }
}