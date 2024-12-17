import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SqlFunctions SQL = new SqlFunctions();
        ProductDAO ProductDAO = new ProductDAO();

        boolean continuar = true;
        while (continuar) {
            System.out.println("Digite seu ID: ");
            String userId = sc.nextLine().trim();
            Client client;

            if (userId.equals("adm")) {
                executarModoAdministrador(sc, ProductDAO, SQL);
            } else if (userId.equals("exit")) {
                continuar = false;
            } else {
                try {

                    int id = Integer.parseInt(userId);
                    client = new Client(id);

                    executarModoCliente(sc, client, ProductDAO);

                } catch (NumberFormatException e) {
                    System.out.println("ID inválido! Tente novamente.");
                }
            }
        }
    }

    private static void executarModoAdministrador(Scanner sc, ProductDAO productDAO, SqlFunctions SQL) {
        boolean modoAdm = true;

        while (modoAdm) {
            System.out.println("Insira a senha:");
            int senha = sc.nextInt();
            sc.nextLine(); // Limpar buffer

            if (senha == 2006) {
                System.out.println("Acesso concedido!");
                boolean continuar = true;

                while (continuar) {
                    productDAO.showProducts("SELECT * FROM produto");
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
                            adicionarProduto(sc, productDAO);
                            break;
                        case "2":
                            alterarProduto(sc, productDAO);
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

    private static void adicionarProduto(Scanner sc, ProductDAO productDAO) {
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
            productDAO.insert(nomeProduto, qtdProduto, precoProduto);
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Erro: Verifique os dados do produto.");
        }
    }

    private static void alterarProduto(Scanner sc, ProductDAO productDAO) {
        System.out.println("ALTERAR PRODUTO");
        System.out.println("'exit' para cancelar");
        System.out.println("----------------");

        System.out.println("Insira o ID do produto a ser alterado:");
        String idProduto = sc.nextLine().trim();
        if (idProduto.equals("exit")) return;

        productDAO.showProducts("SELECT * FROM produto WHERE id = " + idProduto);

        System.out.println("Insira o novo nome (* para manter):");
        String novoNome = sc.nextLine().trim();

        System.out.println("Insira a nova quantidade (* para manter):");
        String novaQtd = sc.nextLine().trim();

        System.out.println("Insira o novo preço (* para manter):");
        String novoPreco = sc.nextLine().trim();

        ProductDAO.update(
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
        Client c = new Client(id);

        c.setSaldo(novoSaldo);
        System.out.println("Saldo atualizado com sucesso!");
    }

    private static void executarModoCliente(Scanner sc, Client client, ProductDAO productDAO) {

        boolean continuar = true;

        while (continuar) {
            System.out.println("------------------------------------");
            System.out.println("Olá, " + client.getNome());
            System.out.println("Saldo: R$" + String.format("%.2f", client.getSaldo()));
            System.out.println("1. Comprar");
            System.out.println("'exit' para sair");
            System.out.println("------------------------------------");
            String opcao = sc.nextLine().trim();

            if (opcao.equals("1")) {
                realizarCompra(sc, client, productDAO);
            } else if (opcao.equals("exit")) {
                continuar = false;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void realizarCompra(Scanner sc, Client client, ProductDAO productDAO) {

        productDAO.showProducts("SELECT * FROM produto");

        System.out.println("Digite o ID do produto a ser comprado:");
        int idProduto = sc.nextInt();

        Product product = new Product(idProduto);

        System.out.println("Digite a quantidade desejada:");
        int quantidade = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        if (quantidade <= product.getQuantidade()) {
            MercadoDAO mercado = new MercadoDAO();
            mercado.compra(client, product, quantidade);
            System.out.println("Compra realizada com sucesso!");
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }
}
