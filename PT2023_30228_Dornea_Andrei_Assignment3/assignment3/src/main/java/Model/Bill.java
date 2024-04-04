package Model;

public record Bill(int idOrder, int quantity, double total) {

    public int getIdOrder() {
        return idOrder;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Bill{" +
                ", idOrder=" + idOrder +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
