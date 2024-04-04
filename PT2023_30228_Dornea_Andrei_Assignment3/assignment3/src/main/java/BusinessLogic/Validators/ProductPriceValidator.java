package BusinessLogic.Validators;

import Model.Product;

public class ProductPriceValidator implements Validator<Product>{ //validator for product price interval
        private static final double MINIMUM_PRICE=0.1;
        private static final double MAXIMUM_PRICE=500;

    /**
     * metoda verifica daca pretul este valid
     * @param product
     */
    @Override
    public void validate(Product product) {
        if(product.getPrice()<MINIMUM_PRICE){
            throw new IllegalArgumentException("The product price is to small!");
        }

        if(product.getPrice()>MAXIMUM_PRICE){
            throw new IllegalArgumentException("The product price is to big!");
        }
    }
}
