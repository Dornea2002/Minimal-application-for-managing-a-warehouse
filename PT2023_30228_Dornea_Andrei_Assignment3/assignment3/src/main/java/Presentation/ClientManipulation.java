package Presentation;

import Application.Reflection;
import BusinessLogic.ClientBLL;
import Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClientManipulation extends JFrame {
    JButton addNewClientButton;
    JButton editClientButton;
    JButton deleteClientButton;
    JButton viewClientsButton;

    public ClientManipulation(){

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        setTitle("Client Page");

        getContentPane().setLayout(null);

        ClientBLL clientBLL=new ClientBLL();
        List<Client> clientList=null;

        addNewClientButton=new JButton();
        addNewClientButton.setText("Add new client");
        addNewClientButton.setBackground(color2);
        addNewClientButton.setBounds(25, 50, 200, 50);
        addNewClientButton.setFont(new Font("Calibri",Font.BOLD,16));
        addNewClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addNewClientButton) {
                    JFrame frame = new JFrame("Add new client");
//                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().setLayout(null);

                    JTextField nameTextField = new JTextField("Name");
                    nameTextField.setFont(new Font("Consolas", Font.PLAIN, 16));
                    nameTextField.setBounds(25, 25, 200, 20);
                    nameTextField.setBackground(color2);
                    frame.getContentPane().add(nameTextField);

                    JTextField addressTextField = new JTextField("Address");
                    addressTextField.setFont(new Font("Consolas", Font.PLAIN, 16));
                    addressTextField.setBounds(25, 60, 200, 20);
                    addressTextField.setBackground(color2);
                    frame.getContentPane().add(addressTextField);

                    JTextField emailTextField = new JTextField("Email");
                    emailTextField.setFont(new Font("Consolas", Font.PLAIN, 16));
                    emailTextField.setBounds(25, 95, 200, 20);
                    emailTextField.setBackground(color2);
                    frame.getContentPane().add(emailTextField);

                    JTextField ageTextField = new JTextField("Age");
                    ageTextField.setFont(new Font("Consolas", Font.PLAIN, 16));
                    ageTextField.setBounds(25, 130, 200, 20);
                    ageTextField.setBackground(color2);
                    frame.getContentPane().add(ageTextField);

                    JButton addButton = new JButton("Add client");
                    addButton.setFont(new Font("Consolas", Font.PLAIN, 15));
                    addButton.setBackground(color2);
                    addButton.setBounds(300, 165, 150, 50);
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Client cl = new Client(nameTextField.getText(), addressTextField.getText(), emailTextField.getText(), Integer.parseInt(ageTextField.getText()));
                            clientBLL.insertClient(cl);
                        }
                    });
                    frame.getContentPane().add(addButton);

                    frame.setSize(500,280);
                    frame.getContentPane().setBackground(color1);
                    frame.setVisible(true);
                }
            }
        });
        getContentPane().add(addNewClientButton);

        editClientButton=new JButton();
        editClientButton.setText("Edit client");
        editClientButton.setBackground(color2);
        editClientButton.setBounds(250, 50, 200, 50);
        editClientButton.setFont(new Font("Calibri",Font.BOLD,16));
        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == editClientButton) {
                    new EditClient();
                }
            }
        });
        getContentPane().add(editClientButton);

        deleteClientButton=new JButton();
        deleteClientButton.setText("Delete client");
        deleteClientButton.setBackground(color2);
        deleteClientButton.setBounds(25, 150, 200, 50);
        deleteClientButton.setFont(new Font("Calibri",Font.BOLD,16));
        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == deleteClientButton){
                    JFrame frame = new JFrame("Delete client");
                    frame.getContentPane().setLayout(null);

                    JTextField nameTextField = new JTextField("Name");
                    nameTextField.setFont(new Font("Consolas", Font.PLAIN, 16));
                    nameTextField.setBounds(25, 15, 400, 30);
                    nameTextField.setBackground(color2);
                    frame.getContentPane().add(nameTextField);

                    JButton deleteButton = new JButton("Delete client");
                    deleteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
                    deleteButton.setBackground(color2);
                    deleteButton.setBounds(300, 60, 150, 40);
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clientBLL.deleteClient(nameTextField.getText());
                        }
                    });
                    frame.getContentPane().add(deleteButton);

                    frame.setSize(500,140);
                    frame.getContentPane().setBackground(color1);
                    frame.setVisible(true);
                }
            }
        });
        getContentPane().add(deleteClientButton);

        viewClientsButton=new JButton();
        viewClientsButton.setText("View clients");
        viewClientsButton.setBackground(color2);
        viewClientsButton.setBounds(250, 150, 200, 50);
        viewClientsButton.setFont(new Font("Calibri",Font.BOLD,16));
        viewClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("View clients");
                frame.getContentPane().setLayout(null);

                List<Client> clients = clientBLL.findAllClients();
                List<String> fieldsList = Reflection.getFields(clients.get(0));
                String[] fields = new String[fieldsList.size()];
                int i = 0;
                for(String field:fieldsList){
                    fields[i++] = field;
                }

                Object[][] data = new Object[clients.size()][fieldsList.size()];
                i=0;
                for(Client client:clients){
                    ArrayList<Object> obj = Reflection.getValues(client);
                    int j=0;
                    for(Object o : obj){
                        data[i][j++] = o;
                    }
                    i++;
                }

                JTable table = new JTable(data, fields);
                table.setPreferredSize(new Dimension(600,400));
                table.setBackground(color2);
                frame.getContentPane().add(table);

                JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);
                scrollPane.setBackground(color2);

                frame.getContentPane().add(scrollPane);

                frame.setLayout(new FlowLayout());
                frame.setSize(700, 500);
                frame.getContentPane().setBackground(color1);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
        getContentPane().add(viewClientsButton);


        setSize(500,300);
        getContentPane().setBackground(color1);
        setVisible(true);
    }
}
