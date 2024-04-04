package Model;

import BusinessLogic.OrderBLL;

public class Order {

    private int idOrder;
    private int idClient;
    private int idProduct;
    private int quantity;
    private double totalPrice;

    public Order(int idOrder, int idClient, int idProduct, int quantity, double totalPrice) {
        super();
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(int idClient, int idProduct, int quantity, double totalPrice) {
        super();
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(){}

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order[" + "idOrder=" + idOrder +
                ", idClient=" + idClient +
                ", idProduct=" + idProduct +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ']';
    }
}
