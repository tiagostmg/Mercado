
import DataBase.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product {

    private final int id;
    private final String name;
    private final double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void showProduct(){
        System.out.println(id + ". " + name.toUpperCase() + " - R$" + String.format("%.2f", price) + " | QUANTIDADE: " + quantity);
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity >= 0) {
            this.quantity = quantity;
            String updateQuery = "UPDATE PRODUTO SET quantidade = ? WHERE id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                // Define os parâmetros
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, id);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar a quantidade do produto");
                e.printStackTrace(System.err);
            }
        }
    }

}
