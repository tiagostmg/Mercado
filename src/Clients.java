import DataBase.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Clients {

    private final ArrayList<Client> list;

    public Clients(){
        this.list = loadClients();
    }

    public ArrayList<Client> loadClients() {
        String query = "SELECT * FROM CLIENTE";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Client> list = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                double balance = resultSet.getDouble("saldo");

                Client client = new Client(id, name, balance);
                list.add(client);
            }

            return list;

        } catch (SQLException e) {
            System.err.println("Erro ao realizar a consulta");
            e.printStackTrace(System.err);
            return new ArrayList<>(); // Retorna lista vazia ao invés de null
        }

    }

    public void showClients(){
        System.out.println("------------------------------------");
        for(Client client : list){
            client.showClient();
        }
        System.out.println("------------------------------------");
    }

}
