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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String formatName(){
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
