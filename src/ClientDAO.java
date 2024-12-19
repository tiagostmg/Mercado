import DataBase.DatabaseConnection;
import DataBase.SqlFunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO {
    public Client getClientById(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> items;

        items = sql.selectById("CLIENTE", id);

        id = Integer.parseInt(items.get(0));
        String name = items.get(1);
        double balance = Double.parseDouble(items.get(2));

        return new Client(id, name, balance);
    }
    public void setClientBalance(Client client, double newBalance){
        String updateQuery = "UPDATE cliente SET saldo = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, client.getId());

            preparedStatement.executeUpdate();
            client.setBalance(newBalance);
            System.out.println("a");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o saldo do cliente");
            e.printStackTrace(System.err);
        }
    }
}