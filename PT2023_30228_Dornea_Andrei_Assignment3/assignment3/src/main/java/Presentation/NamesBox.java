package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class NamesBox extends JComboBox implements ItemListener {

    Client client = null;
    Product product = null;
    ClientBLL clientBLL;
    ProductBLL productBLL;
    String[] names;
    boolean isClient;
    Color color = new Color(254, 232, 176);

    NamesBox(String[] names, boolean isClient) {

        super(names);
        this.names = names;
        this.setBackground(color);
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(150, 30));
        this.setFont(new Font("Consolas", Font.PLAIN, 20));
        this.addItemListener(this);
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        this.isClient = isClient;
        if (isClient == true) {
            client = clientBLL.findClientByName(names[0]);
        } else {
            product = productBLL.findProductByName(names[0]);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this) {
            if (isClient == true) {
                client = clientBLL.findClientByName((String) this.getSelectedItem());
            } else {
                product = productBLL.findProductByName((String) this.getSelectedItem());
            }
        }
    }
}
