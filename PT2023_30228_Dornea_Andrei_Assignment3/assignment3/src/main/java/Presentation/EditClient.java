package Presentation;

import BusinessLogic.ClientBLL;
import Model.Client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClient implements ActionListener {

    JButton editButton;
    JButton updateButton;
    JTextField idTextField;
    JTextField nameTextField;
    JTextField addressTextField;
    JTextField emailTextField;
    JTextField ageTextField;

    EditClient() {

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        JFrame frame = new JFrame("Edit client");

        idTextField = new JTextField("id");
        idTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        idTextField.setPreferredSize(new Dimension(400, 30));
        idTextField.setBackground(color2);

        editButton = new JButton("Edit");
        editButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        editButton.setBackground(color2);
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(100, 40));
        editButton.setBorder(BorderFactory.createLineBorder(color2));
        editButton.addActionListener(this);


        nameTextField = new JTextField("Name");
        nameTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        nameTextField.setPreferredSize(new Dimension(400, 30));
        nameTextField.setBackground(color2);

        addressTextField = new JTextField("Address");
        addressTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        addressTextField.setPreferredSize(new Dimension(400, 30));
        addressTextField.setBackground(color2);

        emailTextField = new JTextField("Email");
        emailTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        emailTextField.setPreferredSize(new Dimension(400, 30));
        emailTextField.setBackground(color2);

        ageTextField = new JTextField("Age");
        ageTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        ageTextField.setPreferredSize(new Dimension(400, 30));
        ageTextField.setBackground(color2);
        idTextField.setBackground(color2);
        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        updateButton.setBackground(color2);
        updateButton.setForeground(Color.WHITE);
        updateButton.setPreferredSize(new Dimension(100, 40));
        updateButton.setBorder(BorderFactory.createLineBorder(color2));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();
                Client cl = new Client(Integer.parseInt(idTextField.getText()), nameTextField.getText(), addressTextField.getText(), emailTextField.getText(), Integer.parseInt(ageTextField.getText()));
                clientBLL.updateClientById(cl, cl.getIdClient());
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(idTextField);
        frame.add(editButton);
        frame.add(nameTextField);
        frame.add(addressTextField);
        frame.add(emailTextField);
        frame.add(ageTextField);
        frame.add(updateButton);
        frame.setSize(500, 320);
        frame.getContentPane().setBackground(color1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == editButton){
            int id = Integer.parseInt(idTextField.getText());
            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.findClientById(id);
            nameTextField.setText(client.getName());
            addressTextField.setText(client.getAddress());
            emailTextField.setText(client.getEmail());
            ageTextField.setText(Long.toString(client.getAge()));
        }
    }
}
