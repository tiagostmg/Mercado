import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlFunctions {

    public ArrayList<ArrayList<String>> select(String comando) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            ArrayList<ArrayList<String>> itensTotal = new ArrayList<>();

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    ArrayList<String> itens = new ArrayList<>();

                    String columnValue = rs.getString(i);
                    String columnName = rsmd.getColumnName(i);

                    itens.add(columnName);
                    itens.add(columnValue);

                    itensTotal.add(itens);

                    System.out.print(columnName.toUpperCase() + ": " + columnValue.toUpperCase() + "  ");
                }
                System.out.println();
            }
            return itensTotal;
        }
        catch (SQLException e) {
            System.out.println("Erro ao realizar a consulta");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> selectById(String table, int id) {
        ArrayList<String> itens = new ArrayList<>();

        // Consulta com o nome da tabela embutido (válida apenas após validação)
        String selectQuery = "SELECT * FROM " + table + " WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectQuery)) {

            stmt.setInt(1, id);
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return itens;
    }

    public void update(String comando) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            PreparedStatement pstmt = connection.prepareStatement(comando);

            int linhasAfetadas = pstmt.executeUpdate();

            System.out.println("Número de linhas afetadas: " + linhasAfetadas);
        }
        catch(SQLException e) {
            System.out.println("Erro ao realizar o update");
            e.printStackTrace();
        }
    }

    public void insert(String comando) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            PreparedStatement pstmt = connection.prepareStatement(comando);

            int linhasAfetadas = pstmt.executeUpdate();

            System.out.println("Inserção realizada com sucesso. Número de linhas afetadas: " + linhasAfetadas);
        }
        catch (SQLException e) {
            System.out.println("Erro ao realizar a inserção");
            e.printStackTrace();
        }
    }

    public void mostra(String table) {
        if (table == null || table.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da tabela não pode ser nulo ou vazio.");
        }

        String query = "SELECT * FROM " + table;

        ArrayList<ArrayList<String>> itens = select(query);

        for(ArrayList<String> e : itens){
            for(int i = 0; i < e.size(); i+= 2){
                System.out.println(e.get(i).toUpperCase() + ": " + e.get(i + 1).toUpperCase() + "  ");
            }
        }
    }


}
