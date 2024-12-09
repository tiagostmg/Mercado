
import java.sql.Connection;
import java.sql.SQLException;

public class MercadoDAO {

    public void compra(Cliente cliente, Produto produto, int qtdCompra) {

        if (cliente == null || produto == null || qtdCompra <= 0) {
            System.out.println("Dados inválidos para realizar a compra.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {

            double saldoCliente = cliente.getSaldo();
            int qtdProduto = produto.getQuantidade();
            double precoProduto = produto.getPreco();

            if (qtdProduto >= qtdCompra) {

                double valorTotal = qtdCompra * precoProduto;

                if(saldoCliente >= valorTotal) {

                    int novaQuantidade = qtdProduto - qtdCompra;
                    produto.setQuantidade(novaQuantidade);

                    double novoSaldo = saldoCliente - valorTotal;
                    cliente.setSaldo(novoSaldo);

                    System.out.println("Compra realizada com sucesso!");
                    if(novaQuantidade == 0) {
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
            e.printStackTrace();
        }
    }
}
