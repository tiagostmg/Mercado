package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlFunctions {

//    public ArrayList<ArrayList<String>> select(String comando) {
//        try (Connection connection = DatabaseConnection.getConnection()) {
//
//            ArrayList<ArrayList<String>> totalItems = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(comando);
//
//            ResultSetMetaData resultSetMetaData = rs.getMetaData();
//            int columnCount = resultSetMetaData.getColumnCount();
//
//            while (rs.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//
//                    ArrayList<String> items = new ArrayList<>();
//
//                    String columnValue = rs.getString(i);
//                    String columnName = resultSetMetaData.getColumnName(i);
//
//                    items.add(columnName);
//                    items.add(columnValue);
//
//                    totalItems.add(items);
//
//                    System.out.print(columnName.toUpperCase() + ": " + columnValue.toUpperCase() + "  ");
//                }
//                System.out.println();
//            }
//            return totalItems;
//        }
//        catch (SQLException e) {
//            System.out.println("Erro ao realizar a consulta");
//            e.printStackTrace(System.err);
//        }
//        return null;
//    }

    public ArrayList<String> selectById(String table, int id) {
        ArrayList<String> itens = new ArrayList<>();

        // Consulta com o nome da tabela embutido (válida apenas após validação)
        String selectQuery = "SELECT * FROM " + table + " WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Adiciona todas as colunas do resultado
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    itens.add(resultSet.getString(i));
                }
            } else {
                System.out.println("Registro não encontrado!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao realizar a consulta.");
            e.printStackTrace(System.err);
        }

        return itens;
    }

    public ArrayList<String> selectByName(String table, String nome) {
        ArrayList<String> itens = new ArrayList<>();

        // Consulta com o nome da tabela embutido (válida apenas após validação)
        String selectQuery = "SELECT * FROM " + table + " WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectQuery)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Adiciona todas as colunas do resultado
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    itens.add(rs.getString(i));
                }
            } else {
                System.out.println("Registro não encontrado!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao realizar a consulta.");
            e.printStackTrace(System.err);
        }

        return itens;
    }

    public void update(String comando) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(comando)) {

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Número de linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao realizar o update");
            e.printStackTrace(System.err);
        }
    }

    public void insert(String comando) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(comando)) {

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Número de linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a inserção");
            e.printStackTrace(System.err);
        }
    }

    public String formatSQL(String query, Object... params) throws SQLException {
        for (Object param : params) {
            query = query.replaceFirst("\\?", param.toString());
        }
        return query;
    }

}
