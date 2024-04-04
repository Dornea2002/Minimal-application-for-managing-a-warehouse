package BusinessLogic;

import BusinessLogic.Validators.ClientFieldsValidator;
import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientBLL { //here we make the operations on clients

    protected static final Logger LOGGER = Logger.getLogger(ClientBLL.class.getName());

    private Validator validator1;
    private Validator validator2;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validator1 = new EmailValidator();
        validator2 = new ClientFieldsValidator();
        clientDAO = new ClientDAO();
    }

    /**
     * Metoda face cautarea unui client in functie de id
     * @param idClient
     * @return client
     */
    public Client findClientById(int idClient) {
        Client client = clientDAO.findById(idClient);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + idClient + "does not exist in data base!");
        }
        return client;
    }

    /**
     * metoda face cautarea unui client in functie de nume
     * @param name
     * @return client
     */
    public Client findClientByName(String name) {
        Client client = clientDAO.findByName(name);
        if (client == null) {
            throw new NoSuchElementException("The client with name =" + name + "does not exist in data base!");
        }
        return client;
    }

    /**
     * metoda face o insertie in tabela de clienti
     * @param client
     */
    public void insertClient(Client client) {
        validator1.validate(client);
        validator2.validate(client);
        clientDAO.insert(client);
    }

    /**
     * metoda returneaza o lista cu toti clientii tabelei
     * @return clientLists
     */
    public List<Client> findAllClients() {
        List<Client> clientList = clientDAO.findAll();
        if (clientList == null) {
            throw new NoSuchElementException("There are no clients in the data base!");
        }
        return clientList;
    }

    /**
     * metoda face un update in functie de id-ul clientului
     * @param client
     * @param idClient
     */

    public void updateClientById(Client client, int idClient) {
        validator1.validate(client);
        validator2.validate(client);
        clientDAO.update(client, idClient);
    }

    /**
     * metoda sterge un client din tabela in functie de nume
     * @param name
     */
    public void deleteClient(String name) {
        Client client = clientDAO.findByName(name);
        if(client != null) {
            LOGGER.log(Level.INFO, "Delete Client with id " + client.getIdClient());
            clientDAO.delete(client.getIdClient());
        }
    }
}
