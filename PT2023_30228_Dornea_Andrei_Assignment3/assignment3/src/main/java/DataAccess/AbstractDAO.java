package DataAccess;

import Connect.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> { //abstract class in which we create the queries for database
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private static final String NAME = "name";
    private static final String ID = "id";

    private final Class<T> type;

    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * metoda creeaza field-uri in baza de date si in functie de aceste si obiectele necesare lor si returneaza o lista de obiecte
     * cu tipul fiecarui field
     * @param resultSet reprezinta cate o linie din baza de date
     * @return list
     */
    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet != null && resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * metoda creeaza un string cu comanda select din toata tabela specificata
     * @return stringBuilder.toString()
     */
    private String createSelectQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM schooldb.");
        stringBuilder.append(type.getSimpleName().toLowerCase());
        return stringBuilder.toString();
    }

    /**
     * metoda creeaza un string cu comanda select Where egal
     * @param field reprezinta campul pe care il folosim pentru querry in clauza where
     * @return return stringBuilder.toString();
     */
    public String createSelectWhereQueryWithEqual(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + " = ? ");
        return stringBuilder.toString();
    }

    /**
     * metoda creeaza un string cu comanda select Where like
     * @param field reprezinta campul pe care il folosim pentru querry in clauza where
     * @return stringBuilder.toString();
     */
    public String createSelectWhereQueryWithLike(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + " LIKE ? ");
        return stringBuilder.toString();
    }

    /**
     * metoda creeaza un querry pentru a sterge o linie din baza de date
     * @param field reprezinta campul pe care il folosim pentru querry in clauza where
     * @return stringBuilder.toString();
     */
    private String createDeleteQuery(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE ");
        stringBuilder.append(field);
        stringBuilder.append(" = ?;");
        return stringBuilder.toString();
    }

    /**
     * metoda creeaza un querry pentru a insera o linie din baza de date
     * @param field reprezinta campurile pe care le folosim pentru querry
     * @return stringBuilder.toString();
     */
    private String createInsertQuery(ArrayList<String> field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO schooldb." + type.getSimpleName() + " (");
        for (int i = 0; i < field.size(); i++) {
            if (i == 0) {
                continue;
            }
            if (i == field.size() - 1) {
                stringBuilder.append(field.get(i) + ") ");
                break;
            }
            stringBuilder.append(field.get(i) + ",");
        }
        stringBuilder.append(" VALUES (");
        for (int i = 0; i < field.size(); i++) {
            if (i == 0) {
                continue;
            }
            if (i == field.size() - 1) {
                stringBuilder.append("?);");
                break;
            }
            stringBuilder.append("?,");
        }
        return stringBuilder.toString();
    }

    /**
     * metoda creeaza un querry pentru a updata o linie din baza de date
     * @param fields reprezinta campurile pe care le folosim pentru querry
     * @return stringBuilder.toString();
     */
    private String createUpdateQuery(ArrayList<String> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + type.getSimpleName());
        stringBuilder.append(" SET ");
        for (int i = 0; i < fields.size(); i++) {
            if (i == fields.size() - 1) {
                stringBuilder.append(fields.get(i) + " = ? ");
                break;
            }
            stringBuilder.append(fields.get(i) + " = ?, ");
        }
        stringBuilder.append("WHERE id" + type.getSimpleName() + " = ?;");
        return stringBuilder.toString();
    }

    /**
     * metoda gaseste si returneaza toate field-urile din baza de date
     * @return createObjects(resultSet);
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * metoda gaseste si returneaza field-urile din baza de date folosind id-ul din parametru
     * @param id reprezinta id-ul dupa care facem cautarea
     * @return getEntityResult(resultSet);
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectWhereQueryWithEqual(ID+type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return getEntityResult(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * metoda gaseste si returneaza field-urile din baza de date folosind numele din parametru
     * @param name reprezinta numele dupa care facem cautarea
     * @return getEntityResult(resultSet);
     */
    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectWhereQueryWithLike(NAME);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%" );
            resultSet = statement.executeQuery();

            return getEntityResult(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * metoda returneaza o singura entitate de resultset
     * @param resultSet reprezinta cate o linie din baza de date
     * @return result.get(0);
     */
    private T getEntityResult(ResultSet resultSet) {
        if(resultSet!=null) {
            List<T> result = createObjects(resultSet);
            if(!result.isEmpty()) {
                return result.get(0);
            }
            LOGGER.log(Level.INFO, "No result was found for type " + type.getSimpleName());
        }
        return null;
    }

    /**
     * metoda preia toate valorile din baza de date folosindu-se de querry-ul din findAll
     * @param t obiect de tipul t
     * @param values valorile pe care dorim sa le preluam
     * @param fields campurile valorilor
     */
    private void getValuesFromDB(T t, ArrayList<Object> values, ArrayList<String> fields) {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field.getName());
            Object value;
            try {
                value = field.get(t);
                values.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * metoda face o insertie in baza de date folosindu-se de querry-ul din insert
     * @param t
     */
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<String>();
        getValuesFromDB(t, values, fields);
        String query = createInsertQuery(fields);
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (int i = 1; i < fields.size(); i++) {
                if (values.get(i) instanceof Integer) {
                    statement.setInt(i, (Integer) values.get(i));
                }
                if (values.get(i) instanceof String) {
                    statement.setString(i, values.get(i).toString());
                }
                if (values.get(i) instanceof Double) {
                    statement.setDouble(i, (Double) values.get(i));
                }
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

    /**
     * metoda face update in baza de date folosindu-se de querry-ul din Update
     * @param t
     * @param id
     */
    public void update(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<String>();
        getValuesFromDB(t, values, fields);
        String query = createUpdateQuery(fields);
        try {
            int i;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (i = 0; i < fields.size(); i++) {
                if (values.get(i) instanceof Integer) {
                    statement.setInt(i + 1, ((Integer) values.get(i)).intValue());
                }
                if (values.get(i) instanceof String) {
                    statement.setString(i + 1, values.get(i).toString());
                }
                if (values.get(i) instanceof Double) {
                    statement.setDouble(i + 1, ((Double) values.get(i)).doubleValue());
                }
            }
            statement.setInt(i + 1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

    /**
     * metoda sterge o linie din baza de date folosindu-se de querry-ul din Delete
     * @param id
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id"+type.getSimpleName());
        T t = findById(id);
        try {
            int i;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }
}