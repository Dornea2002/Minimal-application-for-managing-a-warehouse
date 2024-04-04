package DataAccess;

import Connect.ConnectionFactory;
import Model.Order;
import Model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class OrderDAO extends AbstractDAO<Order> {//the class is a subclass of AbstractDAO

    //in this class we define the original class methods

    /**
     * metoda insereaza o comanda in baza de date
     * @param fields reprezinta campurile pe care le folosim pentru querry
     * @return stringBuilder.toString();
     */
    private String createInsertOrderInDB(ArrayList<String> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO schooldb.order (");

        for (String string : fields) {
            if (fields.get(0).equals(string)) {
                continue;
            }
            if (fields.get(fields.size() - 1).equals(string)) {
                stringBuilder.append(string + ") ");
                break;
            }
            stringBuilder.append(string + ", ");
        }
        stringBuilder.append(" VALUES (");
        for (String string : fields) {
            if (fields.get(0).equals(string)) {
                continue;
            }
            if (fields.get(fields.size() - 1).equals(string)) {
                stringBuilder.append("?);");
                break;
            }
            stringBuilder.append("?,");
        }
        return stringBuilder.toString();
    }

    /**
     *  metoda adauga o comanda in baza de date si returneaza id-ul comenzii
     * @param idClient reprezinta id-ul clientului care plaseaza comanda
     * @param idProdus reprezinta id-ul produsului comandat
     * @param quantity reprezinta cantitatea comenzii
     * @param product reprezinta produsul comandat
     * @return id id-ul comenzii
     */
    public int addOrder(int idClient, int idProdus, int quantity, Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        Order o = new Order();
        Class<?> c = o.getClass();
        ArrayList<String> fields = new ArrayList<>();
        for (Field fd : c.getDeclaredFields()) {
            fields.add(fd.getName());
        }
        String query = createInsertOrderInDB(fields);
        try {
            connection = ConnectionFactory.getConnection();
            String[] generatedColumns = { "idOrder" };
            statement = connection.prepareStatement(query, generatedColumns);

            double price = product.getPrice() * quantity;
            int stockLeft = product.getStock() - quantity;

            product.setStock(stockLeft);
            statement.setInt(1, idClient);
            statement.setInt(2, idProdus);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);

            statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                LOGGER.log(Level.INFO, "Inserted order with ID - " + id); // display inserted record
                return id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
        return 0;
    }
}
