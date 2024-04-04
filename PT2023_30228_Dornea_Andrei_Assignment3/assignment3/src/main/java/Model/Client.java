package Model;

public class Client {
    private int idClient;
    private String name;
    private String address;
    private String email;
    private Integer age;

    public Client(String name, String address, String email, Integer age) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public Client(int idClient, String name, String address, String email, Integer age) {
        super();
        this.idClient = idClient;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public Client() {
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client[" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ']';
    }
}
