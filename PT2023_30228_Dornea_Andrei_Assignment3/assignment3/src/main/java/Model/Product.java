package Model;

public class Product {
    private int idProduct;
    private String name;
    private int stock;
    private double price;

    public Product(int idProduct, String name, int stock, double price) {
        super();
        this.idProduct = idProduct;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Product(String name, int stock, double price) {
        super();
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Product() {
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product[" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ']';
    }
}
