import DataBase.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class MarketDAO {

    public void buy(Client client, Product product, int buyQty) {

        if (client == null || product == null || buyQty <= 0) {
            System.out.println("Dados inválidos para realizar a compra.");
            return;
        }

        try (Connection _ = DatabaseConnection.getConnection()) {

            double clientBalance = client.getBalance();
            int productQty = product.getQuantity();
            double productPrice = product.getPrice();
            ProductDAO productDAO = new ProductDAO();

            if (productQty >= buyQty) {

                double totalValue = buyQty * productPrice;

                if(clientBalance >= totalValue) {

                    int newQty = productQty - buyQty;
                    productDAO.setProductQuantity(product, newQty);

                    double newBalance = clientBalance - totalValue;

                    ClientDAO clientDAO = new ClientDAO();
                    clientDAO.setClientBalance(client, newBalance);

                    if(newQty == 0) {
                        System.out.println("Estoque do produto zerado");
                    }

                } else {
                    System.out.println("Saldo do cliente insuficiente");
                }
            } else {
                System.out.println("Quantidade insuficiente em estoque.");
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao realizar a consulta");
            e.printStackTrace(System.err);
        }
    }

}
