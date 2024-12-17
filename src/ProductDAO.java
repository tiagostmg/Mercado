
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO {

    public void showProducts(String comando) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            System.out.println("------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");

                System.out.println(id + ". " + nome.toUpperCase() + " - R$" + String.format("%.2f", preco) + " | QUANTIDADE: " + quantidade);
            }
            System.out.println("------------------------------------");
        }
        catch (SQLException e) {
            System.out.println("Erro ao realizar a consulta");
            e.printStackTrace(System.err);
        }
    }

    public void insert(String nomeProduto, int qtdProduto, double precoProduto) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO PRODUTO (nome, quantidade, preco) VALUES (?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setString(1, nomeProduto.toLowerCase());
            pstmt.setInt(2, qtdProduto);
            pstmt.setDouble(3, precoProduto);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                Product p = new Product(nomeProduto.toLowerCase());
                System.out.println("------------------------------------");
                System.out.println("Produto adicionado com sucesso:");
                System.out.println(p.getId() + ". " + nomeProduto.toUpperCase() + " - R$" + String.format("%.2f", precoProduto) + " | QUANTIDADE: " + qtdProduto);
                System.out.println("------------------------------------");

            } else {
                System.out.println("Falha ao adicionar o produto.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o produto no banco de dados.");
            e.printStackTrace(System.err);
        }
    }

    public static void update(int id, String nomeProduto, int qtdProduto, double precoProduto) {
        String updateQuery = "UPDATE PRODUTO SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setString(1, nomeProduto);  // Nome do produto
            stmt.setInt(2, qtdProduto);      // Quantidade do produto
            stmt.setDouble(3, precoProduto); // Preço do produto
            stmt.setInt(4, id);              // ID do produto a ser alterado

            int rowsAffected = stmt.executeUpdate(); // Executa o UPDATE
            if (rowsAffected > 0) {
                System.out.println("Produto alterado com sucesso.");
            } else {
                System.out.println("Erro: Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao alterar o produto.");
            e.printStackTrace(System.err);
        }
    }

//    public int qtdItens(String tabela) {
//        int result = 0;
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tabela);
//
//            if (rs.next()) {
//                result = rs.getInt(1);
//            }
//        }
//        catch (SQLException e) {
//            System.out.println("Erro ao realizar a consulta");
//            e.printStackTrace(System.err);
//        }
//        return result;
//
//    }
}
