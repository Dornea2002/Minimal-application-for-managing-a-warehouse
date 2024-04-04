package DataAccess;

import Connect.ConnectionFactory;
import Model.Bill;
import Model.Order;
import Model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillDAO extends AbstractDAO<Bill>{
    /**
     * metoda creaza insertia in baza de date cu ajutorul functilor din abstract dao
     * @param fields fields reprezinta campurile pe care le folosim pentru querry
     * @return stringBuilder.toString();
     */
    private String createInsertBillInDB(ArrayList<String> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO schooldb.log (");

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
     * Metoda adauga o factura in baza de date
     * @param idOrder reprezinta id-ul comenzii
     * @param quantity reprezinta cantitatea comenzii
     * @param total reprezinta totalul comenzii
     */
    public void addBill(int idOrder, int quantity, double total) {
        Connection connection = null;
        PreparedStatement statement = null;
        Bill bill = new Bill(idOrder, quantity, total);
        Class<?> c = bill.getClass();
        ArrayList<String> fields = new ArrayList<>();
        for (Field fd : c.getDeclaredFields()) {
            fields.add(fd.getName());
        }
        String query = createInsertBillInDB(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, idOrder);
            statement.setInt(2, quantity);
//            statement.setDouble(3, total);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

}
