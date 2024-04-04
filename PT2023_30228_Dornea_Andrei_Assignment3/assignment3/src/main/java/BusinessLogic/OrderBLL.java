package BusinessLogic;

import BusinessLogic.Validators.QuantityOrderingValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.OrderDAO;
import Model.Bill;
import Model.Order;
import Model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL { //here we make the operations on orders\
    private OrderDAO orderDAO;
    private Validator validator;

    public OrderBLL(){
        orderDAO = new OrderDAO();
        validator = new QuantityOrderingValidator();
    }

    /**
     * metoda plaseaza o noua comanda folosind parametrii si o insereaza in tabela Order
     * @param idClient
     * @param idProdus
     * @param quantity
     */

    public void placeNewOrder(int idClient, int idProdus, int quantity){
        Order order= new Order(idClient, idProdus, quantity, 0);
        validator.validate(order);

        ProductBLL productBLL=new ProductBLL();
        Product product=productBLL.findProductById(idProdus);

        int orderID = orderDAO.addOrder(idClient, idProdus, quantity, product);
        productBLL.updateProduct(product, idProdus);

        Bill bill=new Bill(orderID, order.getQuantity(), product.getPrice() * quantity);

        BillBLL billBLL=new BillBLL();

        billBLL.insertOrderBill(bill);

        if(product.getStock()==0){
            productBLL.deleteProduct(product.getName());
        }
    }

    /**
     * metoda returneaza o lista cu toate comenzile
     * @return orderList
     */
    public List<Order> findAllOrders(){
        List<Order> orderList=orderDAO.findAll();
        if(orderList==null){
            throw new NoSuchElementException("There are no existing orders in data base!");
        }
        return orderList;
    }
}
