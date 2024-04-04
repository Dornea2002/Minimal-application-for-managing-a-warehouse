package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    JLabel titleLabel;
    JButton processClientsButton;
    JButton processProductsButton;
    JButton placeOrderButton;
    JButton viewOrderButton;

    public View() {

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        this.setTitle("Main Frame");

        titleLabel=new JLabel("Hello! Please choose the action you would like to proceed.");
        titleLabel.setBounds(75, 15, 450, 50);
        titleLabel.setFont(new Font("Calibri",Font.BOLD,14));
        this.getContentPane().add(titleLabel);

        processClientsButton = new JButton("Process clients");
        processClientsButton.setFont(new Font("Calibri",Font.BOLD,12));
        processClientsButton.setBounds(75, 100, 120,30);
        processClientsButton.setBackground(color2);
        processClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == processClientsButton){
                    new ClientManipulation();
                }
            }
        });
        this.getContentPane().add(processClientsButton);

        processProductsButton = new JButton("Process products");
        processProductsButton.setFont(new Font("Calibri",Font.BOLD,12));
        processProductsButton.setBounds(305, 100, 120,30);
        processProductsButton.setBackground(color2);
        processProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == processProductsButton){
                    new ProductManipulation();
                }
            }
        });
        this.getContentPane().add(processProductsButton);

        placeOrderButton = new JButton("Place order");
        placeOrderButton.setFont(new Font("Calibri",Font.BOLD,12));
        placeOrderButton.setBounds(75, 175, 120,30);
        placeOrderButton.setBackground(color2);
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == placeOrderButton){
                    new OrderManipulation();
                }
            }
        });
        this.getContentPane().add(placeOrderButton);

        viewOrderButton = new JButton("View orders");
        viewOrderButton.setFont(new Font("Calibri",Font.BOLD,12));
        viewOrderButton.setBounds(305, 175, 120,30);
        viewOrderButton.setBackground(color2);
        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == viewOrderButton){
                    new ViewOrders();
                }
            }
        });
        this.getContentPane().add(viewOrderButton);


        setSize(500,300);
        getContentPane().setBackground(color1);
        setVisible(true);
    }
}
