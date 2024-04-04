package BusinessLogic;

import BusinessLogic.Validators.ProductPriceValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL { //here we make the operations on products
    private ProductDAO productDAO;
    private Validator validator;

    public ProductBLL(ProductDAO productDAO) {
        this.productDAO = productDAO;
        validator=new ProductPriceValidator();
    }

    public ProductBLL() {
        validator=new ProductPriceValidator();
        productDAO=new ProductDAO();
    }

    /**
     * metoda gaseste un produs folosind id-ul
     * @param idProduct
     * @return product
     */
    public Product findProductById(int idProduct) {
        Product product = productDAO.findById(idProduct);
        validator.validate(product);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + idProduct + " does not exist in data base!");
        }
        return product;
    }

    /**
     * metoda gaseste un produs folosind numele acestuia
     * @param name
     * @return product
     */
    public Product findProductByName(String name) {
        Product product = productDAO.findByName(name);
        if (product == null) {
            throw new NoSuchElementException("The product with name = " + name + " does not exist in data base!");
        }
        return product;
    }

    /**
     * metoda face o insertie in tabela product
     * @param product
     */
    public void insertProduct(Product product) {
        validator.validate(product);
        productDAO.insert(product);
    }

    /**
     * metoda returneaza o lista cu toate produsele din baza de date
     * @return productList
     */
    public List<Product> findAllProducts() {
        List<Product> productList = productDAO.findAll();
        if (productList == null) {
            throw new NoSuchElementException("There are no products in data base!");
        }
        return productList;
    }

    /**
     * Metoda face modificari asupra unui produs folosind id-ul acestuia
     * @param product
     * @param idProduct
     */
    public void updateProduct(Product product, int idProduct) {
        validator.validate(product);
        productDAO.update(product, idProduct);
    }
    public void updateProductById(Product pd, int id) {
        productDAO.update(pd, id);
    }

    /**
     * metoda sterge un produs folosind numele acestuia
     * @param name
     */
    public void deleteProduct(String name) {
        Product product=productDAO.findByName(name);
        productDAO.delete(product.getIdProduct());
    }
}
