import DataBase.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Products {

    private final ArrayList<Product> list;

    public Products(){
        this.list = loadProducts();
    }

    public ArrayList<Product> loadProducts() {
        String query = "SELECT * FROM PRODUTO";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Product> list = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                double price = resultSet.getDouble("preco");
                int quantity = resultSet.getInt("quantidade");

                Product product = new Product(id, name, price, quantity);
                list.add(product);
            }

            return list;

        } catch (SQLException e) {
            System.err.println("Erro ao realizar a consulta");
            e.printStackTrace(System.err);
            return new ArrayList<>(); // Retorna lista vazia ao invés de null
        }

    }

    public void showProducts(){
        System.out.println("------------------------------------");
        for(Product product : list){
            product.showProduct();
        }
        System.out.println("------------------------------------");
    }
}
