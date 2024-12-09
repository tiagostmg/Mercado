
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Produto {

    private final int id;
    private final String nome;
    private final double preco;
    private int quantidade;

    public Produto(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> itens = new ArrayList<>();

        itens = sql.selectById("PRODUTO", id);

        this.id = Integer.parseInt(itens.get(0));
        this.nome = itens.get(1);
        this.preco = Double.parseDouble(itens.get(2));
        this.quantidade = Integer.parseInt(itens.get(3));
    }

    public Produto(String nome) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> itens = new ArrayList<>();

        itens = sql.selectByName("PRODUTO", nome);

        this.id = Integer.parseInt(itens.get(0));
        this.nome = itens.get(1);
        this.preco = Double.parseDouble(itens.get(2));
        this.quantidade = Integer.parseInt(itens.get(3));
    }

    public void imprimir() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("PRODUTO " + id + ":");
        System.out.println(nome);
        System.out.println("Preço: " + df.format(preco));
        System.out.println("Estoque: " + quantidade);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade >= 0) {
            this.quantidade = quantidade;
            String updateQuery = "UPDATE PRODUTO SET quantidade = ? WHERE id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

                // Define os parâmetros
                stmt.setInt(1, quantidade);
                stmt.setInt(2, id);

                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar a quantidade do produto");
                e.printStackTrace();
            }
        }
    }

}
