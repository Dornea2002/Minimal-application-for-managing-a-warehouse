package BusinessLogic.Validators;

import BusinessLogic.ProductBLL;
import Model.Order;
import Model.Product;

public class QuantityOrderingValidator implements Validator<Order> { //check that quantity is not negative
    /**
     * metoda verifica daca cantitatea comandata este mai mica sau egala decat stocul
     * @param order
     */
    @Override
    public void validate(Order order) {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(order.getIdProduct());
        if (order.getQuantity() < 1 || order.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("The Product Stock is under quantity you have ordered!");
        }
    }
}
