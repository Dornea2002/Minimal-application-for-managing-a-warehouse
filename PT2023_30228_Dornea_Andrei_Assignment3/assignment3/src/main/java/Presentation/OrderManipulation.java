package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderManipulation extends JFrame {

    JLabel chooseClientLabel;
    JLabel chooseProductLabel;
    JTextField quantityTextField;
    JButton orderButton;
    OrderManipulation() {

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        OrderBLL orderBLL = new OrderBLL();
        chooseClientLabel = new JLabel("Choose Client");
        chooseClientLabel.setFont(new Font("Calibri",Font.BOLD,18));
        chooseClientLabel.setForeground(Color.WHITE);
        chooseClientLabel.setSize(200,20);
        chooseClientLabel.setBackground(color2);

        ClientBLL clientBLL = new ClientBLL();
        List<Client> clients = clientBLL.findAllClients();
        String[] names = new String[clients.size()];
        int i = 0;
        for(Client cl:clients){
            names[i++] = cl.getName();
        }
        NamesBox nb1 = new NamesBox(names,true);

        chooseProductLabel = new JLabel("Choose Product");
        chooseProductLabel.setFont(new Font("Calibri",Font.BOLD,18));
        chooseProductLabel.setForeground(Color.WHITE);
        chooseProductLabel.setSize(200,20);
        chooseProductLabel.setBackground(color2);

        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findAllProducts();
        String[] names1 = new String[products.size()];
        i = 0;
        for(Product pd:products){
            names1[i++] = pd.getName();
        }
        NamesBox nb2 = new NamesBox(names1,false);

        quantityTextField = new JTextField("Quantity");
        quantityTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        quantityTextField.setPreferredSize(new Dimension(300, 30));
        quantityTextField.setBackground(color2);

        orderButton = new JButton("Order");
        orderButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        orderButton.setBackground(color2);
        orderButton.setForeground(Color.WHITE);
        orderButton.setPreferredSize(new Dimension(100, 40));
        orderButton.setBorder(BorderFactory.createLineBorder(Color.black));
        orderButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(e.getSource() == orderButton) {
                                        Product p = nb2.product;
                                        int quantity = Integer.parseInt(quantityTextField.getText());
                                        if (p.getStock() < quantity) {
                                            final JOptionPane optionPane = new JOptionPane(
                                                    "The quantity is bigger than the stock!\n" +
                                                            "Would you like to try again?",
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    JOptionPane.YES_NO_OPTION);
                                            optionPane.setBackground(color1);

                                            final JDialog dialog = new JDialog((Dialog) getOwner(), "Click a button", true);
                                            dialog.setBackground(color1);
                                            dialog.setContentPane(optionPane);
                                            dialog.setDefaultCloseOperation(
                                                    JDialog.DO_NOTHING_ON_CLOSE);

                                            dialog.addWindowListener(new WindowAdapter() {
                                                public void windowClosing(WindowEvent we) {
                                                    optionPane.setVisible(false);
                                                    dialog.setVisible(false);
                                                }
                                            });
                                            optionPane.addPropertyChangeListener(
                                                    new PropertyChangeListener() {
                                                        public void propertyChange(PropertyChangeEvent e) {
                                                            String prop = e.getPropertyName();

                                                            if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                                                                //If you were going to check something
                                                                //before closing the window, you'd do
                                                                //it here.
                                                                dialog.setVisible(false);
                                                            }
                                                        }
                                                    });
                                            dialog.pack();
                                            dialog.setVisible(true);
                                            int value = ((Integer) optionPane.getValue()).intValue();
                                            if (value == JOptionPane.YES_OPTION) {
                                                optionPane.setVisible(false);
                                                dialog.setVisible(false);
                                            } else if (value == JOptionPane.NO_OPTION) {
                                                optionPane.setVisible(false);
                                                dialog.setVisible(false);
                                                OrderManipulation.super.dispose();
                                            }
                                        } else {
                                            FileWriter file;
                                            BufferedWriter writer;
                                            try {
                                                file = new FileWriter("nota.txt");
                                                writer = new BufferedWriter(file);
                                                double rez = quantity * p.getPrice();
                                                writer.write("--------------------- BILL ---------------------\n\n\n");
                                                writer.write("The client " + nb1.client.getName() + " placed today the order:\n");
                                                writer.write(p.getName() + "--------------------------------" + quantity+"\n");
                                                writer.write(p.getPrice() + "-----------------------------------" + rez+"\n");
                                                writer.write("----------------------------Total: " + rez+"\n\n\n");
                                                writer.write("-----------------------Thank you-----------------------");
                                                writer.close();
                                            } catch (IOException ei) {
                                                ei.printStackTrace();
                                            }
                                            orderBLL.placeNewOrder(nb1.client.getIdClient(), nb2.product.getIdProduct(), quantity);
                                        }
                                    }
                                }
                            }
        );

        this.add(chooseClientLabel);
        this.add(nb1);
        this.add(chooseProductLabel);
        this.add(nb2);
        this.add(quantityTextField);
        this.add(orderButton);
        this.setLayout(new FlowLayout());
        this.setSize(400, 400);
        this.setTitle("Order Page");
        this.getContentPane().setBackground(color1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }
}
