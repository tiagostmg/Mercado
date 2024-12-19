import DataBase.DatabaseConnection;
import DataBase.SqlFunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {

    public Product getProductById(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> items;

        items = sql.selectById("PRODUTO", id);

        id = Integer.parseInt(items.get(0));
        String name = items.get(1);
        double price = Double.parseDouble(items.get(2));
        int quantity = Integer.parseInt(items.get(3));

        return new Product(id, name, price, quantity);
    }

    public Product getProductByName(String name) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> items;

        items = sql.selectByName("PRODUTO", name);

        int id = Integer.parseInt(items.get(0));
        name = items.get(1);
        double price = Double.parseDouble(items.get(2));
        int quantity = Integer.parseInt(items.get(3));

        return new Product(id, name, price, quantity);
    }

    public void insertProduct(String productName, int productQty, double productPrice) {
        String insertSQL = "INSERT INTO PRODUTO (nome, quantidade, preco) VALUES (?, ?, ?)";

        try {
            SqlFunctions SQL = new SqlFunctions();
            String formattedSQL = SQL.formatSQL(insertSQL, productName.toLowerCase(), productQty, productPrice);
            SQL.insert(formattedSQL);

            System.out.println("Produto adicionado com sucesso:");
            ProductDAO productDAO = new ProductDAO();
            productDAO.getProductByName(productName.toLowerCase()).showProduct();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir o produto no banco de dados.");
            e.printStackTrace(System.err);
        }
    }

    public static void updateProduct(int id, String productName, int productQty, double productPrice) {
        String updateQuery = "UPDATE PRODUTO SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try {
            SqlFunctions SQL = new SqlFunctions();
            String formattedSQL = SQL.formatSQL(updateQuery, productName, productQty, productPrice, id);
            SQL.update(formattedSQL);

            System.out.println("Produto alterado com sucesso.");
            ProductDAO productDAO = new ProductDAO();
            productDAO.getProductByName(productName.toLowerCase()).showProduct();

        } catch (SQLException e) {
            System.out.println("Erro ao alterar o produto.");
            e.printStackTrace(System.err);
        }
    }

    public void setProductQuantity(Product product, int newQuantity){

        if(product.getQuantity() >= 0) {

            String updateQuery = "UPDATE PRODUTO SET quantidade = ? WHERE id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();

                product.setQuantity(newQuantity);

            } catch (SQLException e) {
                System.out.println("Erro ao atualizar a quantidade do produto");
                e.printStackTrace(System.err);
            }
        }

    }

}
