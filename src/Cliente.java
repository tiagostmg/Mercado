
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cliente {

    private final int id;
    private final String nome;
    private double saldo;

    public Cliente(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> itens = new ArrayList<>();

        itens = sql.selectById("PRODUTO", id);

        this.id = Integer.parseInt(itens.get(0));
        this.nome = itens.get(1);
        this.saldo = Double.parseDouble(itens.get(2));
    }

    public Cliente getClienteById(int id) {
        Cliente cliente = new Cliente(id);
        return cliente;
    }

    public void imprimir() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(nome);
        System.out.println("Saldo: " + df.format(saldo));
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
        String updateQuery = "UPDATE CLIENTE SET saldo = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setDouble(1, saldo);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o saldo do cliente");
            e.printStackTrace();
        }
    }
}
