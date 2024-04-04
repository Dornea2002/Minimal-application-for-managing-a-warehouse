package BusinessLogic.Validators;

import Model.Client;

public class ClientFieldsValidator implements Validator<Client> {//check if name and address is valid

    private static final int MAXIMUM_LENGTH = 50;

    /**
     * metoda verifica daca adresa si numele clientului sunt valide
     * @param client
     */

    @Override
    public void validate(Client client) {
        if (client.getAddress().length() > MAXIMUM_LENGTH || client.getName().length() < 1) {
            throw new IllegalArgumentException("The client name is not valid!");
        }
        if (client.getAddress().length() > MAXIMUM_LENGTH || client.getAddress().length() < 1) {
            throw new IllegalArgumentException("The client address is not valid!");
        }
    }

}
