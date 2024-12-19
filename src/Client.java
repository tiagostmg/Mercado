
import DataBase.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client {

    private final int id;
    private final String name;
    private double balance;

    public Client(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void showClient(){
        System.out.println(formatName() + " - ID: " + id + " | Saldo: " + String.format("%.2f", balance));
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        String updateQuery = "UPDATE CLIENTE SET saldo = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setDouble(1, balance);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o saldo do cliente");
            e.printStackTrace(System.err);
        }
    }

    public String formatName(){
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
