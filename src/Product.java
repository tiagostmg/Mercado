public class Product {

    private final int id;
    private final String name;
    private final double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void showProduct(){
        System.out.println(id + ". " + name.toUpperCase() + " - R$" + String.format("%.2f", price) + " | QUANTIDADE: " + quantity);
    }

    public int getId(){
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
