import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SqlFunctions SQL = new SqlFunctions();
        ProdutoDAO ProdutoDAO = new ProdutoDAO();

        while (true) {
            System.out.println("Digite seu ID: ");
            String usuarioId = sc.nextLine().trim();
            Cliente cliente = null;

            if (usuarioId.equals("adm")) {
                executarModoAdministrador(sc, ProdutoDAO, SQL);
            } else {
                try {

                    int id = Integer.parseInt(usuarioId);
                    cliente = new Cliente(id);

                    executarModoCliente(sc, cliente, ProdutoDAO);

                } catch (NumberFormatException e) {
                    System.out.println("ID inválido! Tente novamente.");
                }
            }
        }
    }

    private static void executarModoAdministrador(Scanner sc, ProdutoDAO produtoDAO, SqlFunctions SQL) {
        boolean modoAdm = true;

        while (modoAdm) {
            System.out.println("Insira a senha:");
            int senha = sc.nextInt();
            sc.nextLine(); // Limpar buffer

            if (senha == 2006) {
                System.out.println("Acesso concedido!");
                boolean continuar = true;

                while (continuar) {
                    produtoDAO.select("SELECT * FROM produto");
                    SQL.select("SELECT * FROM cliente");

                    System.out.println("------------------------------------");
                    System.out.println("1. Adicionar produto");
                    System.out.println("2. Alterar produto");
                    System.out.println("3. Alterar saldo de cliente");
                    System.out.println("'exit' para sair");
                    System.out.println("------------------------------------");
                    String opcao = sc.nextLine().trim();

                    switch (opcao) {
                        case "1":
                            adicionarProduto(sc, produtoDAO);
                            break;
                        case "2":
                            alterarProduto(sc, produtoDAO);
                            break;
                        case "3":
                            alterarSaldoCliente(sc, SQL);
                            break;
                        case "exit":
                            continuar = false;
                            modoAdm = false;
                            break;
                        default:
                            System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            } else {
                System.out.println("Senha incorreta.");
                modoAdm = false;
            }
        }
    }

    private static void adicionarProduto(Scanner sc, ProdutoDAO produtoDAO) {
        System.out.println("ADCIONAR PRODUTO");
        System.out.println("'exit' para cancelar");
        System.out.println("----------------");

        System.out.println("Insira o nome do produto:");
        String nomeProduto = sc.nextLine().trim();
        if (nomeProduto.equals("exit")) return;

        System.out.println("Insira a quantidade do produto:");
        int qtdProduto = sc.nextInt();
        System.out.println("Insira o preço do produto:");
        double precoProduto = sc.nextDouble();
        sc.nextLine(); // Limpar buffer

        if (!nomeProduto.isEmpty() && qtdProduto > 0 && precoProduto > 0) {
            produtoDAO.insert(nomeProduto, qtdProduto, precoProduto);
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Erro: Verifique os dados do produto.");
        }
    }

    private static void alterarProduto(Scanner sc, ProdutoDAO produtoDAO) {
        System.out.println("ALTERAR PRODUTO");
        System.out.println("'exit' para cancelar");
        System.out.println("----------------");

        System.out.println("Insira o ID do produto a ser alterado:");
        String idProduto = sc.nextLine().trim();
        if (idProduto.equals("exit")) return;

        produtoDAO.select("SELECT * FROM produto WHERE id = " + idProduto);

        System.out.println("Insira o novo nome (* para manter):");
        String novoNome = sc.nextLine().trim();

        System.out.println("Insira a nova quantidade (* para manter):");
        String novaQtd = sc.nextLine().trim();

        System.out.println("Insira o novo preço (* para manter):");
        String novoPreco = sc.nextLine().trim();

        produtoDAO.update(
                Integer.parseInt(idProduto),
                novoNome.equals("*") ? null : novoNome,
                novaQtd.equals("*") ? -1 : Integer.parseInt(novaQtd),
                novoPreco.equals("*") ? -1 : Double.parseDouble(novoPreco)
        );

        System.out.println("Produto atualizado com sucesso!");
    }

    private static void alterarSaldoCliente(Scanner sc, SqlFunctions SQL) {
        System.out.println("ALTERAR SALDO DE CLIENTE");
        System.out.println("'exit' para cancelar");
        System.out.println("----------------");

        System.out.println("Insira o ID do cliente:");
        String idCliente = sc.nextLine().trim();
        if (idCliente.equals("exit")) return;

        SQL.select("SELECT * FROM cliente WHERE id = " + idCliente);

        System.out.println("Insira o novo saldo:");
        double novoSaldo = sc.nextDouble();
        sc.nextLine(); // Limpar buffer

        int id = Integer.parseInt(idCliente);
        Cliente c = new Cliente(id);

        c.setSaldo(novoSaldo);
        System.out.println("Saldo atualizado com sucesso!");
    }

    private static void executarModoCliente(Scanner sc, Cliente cliente, ProdutoDAO produtoDAO) {

        boolean continuar = true;

        while (continuar) {
            System.out.println("------------------------------------");
            System.out.println("Olá, " + cliente.getNome());
            System.out.println("Saldo: R$" + String.format("%.2f", cliente.getSaldo()));
            System.out.println("1. Comprar");
            System.out.println("'exit' para sair");
            System.out.println("------------------------------------");
            String opcao = sc.nextLine().trim();

            if (opcao.equals("1")) {
                realizarCompra(sc, cliente, produtoDAO);
            } else if (opcao.equals("exit")) {
                continuar = false;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void realizarCompra(Scanner sc, Cliente cliente, ProdutoDAO produtoDAO) {
        produtoDAO.select("SELECT * FROM produto");
        System.out.println("Digite o ID do produto a ser comprado:");
        int idProduto = sc.nextInt();

        Produto produto = new Produto(idProduto);

        System.out.println("Digite a quantidade desejada:");
        int quantidade = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        if (quantidade <= produto.getQuantidade()) {
            MercadoDAO mercado = new MercadoDAO();
            mercado.compra(cliente, produto, quantidade);
            System.out.println("Compra realizada com sucesso!");
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }
}
