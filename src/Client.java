
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client {

    private final int id;
    private final String nome;
    private double saldo;

    public Client(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> items;

        items = sql.selectById("CLIENTE", id);

        this.id = Integer.parseInt(items.get(0));
        this.nome = items.get(1);
        this.saldo = Double.parseDouble(items.get(2));
    }

//    public Client getClienteById(int id) {
//        return new Client(id);
//    }

//    public void imprimir() {
//        DecimalFormat df = new DecimalFormat("#.00");
//        System.out.println(nome);
//        System.out.println("Saldo: " + df.format(saldo));
//    }

//    public int getId() {
//        return id;
//    }

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
            e.printStackTrace(System.err);
        }
    }
}
