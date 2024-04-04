package Presentation;

import BusinessLogic.ProductBLL;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProduct implements ActionListener {

    JButton editButton;
    JButton updateButton;
    JTextField idTextField;
    JTextField nameTextField;
    JTextField stockTextField;
    JTextField priceTextField;

    EditProduct() {
        JFrame frame = new JFrame("Edit product");

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        idTextField = new JTextField("id");
        idTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        idTextField.setPreferredSize(new Dimension(400, 30));
        idTextField.setBackground(color2);

        editButton = new JButton("Edit");
        editButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        editButton.setBackground(color2);
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(100, 40));
        editButton.setBorder(BorderFactory.createLineBorder(Color.black));
        editButton.addActionListener(this);


        nameTextField = new JTextField("Name");
        nameTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        nameTextField.setPreferredSize(new Dimension(400, 30));
        nameTextField.setBackground(color2);

        stockTextField = new JTextField("Stock");
        stockTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        stockTextField.setPreferredSize(new Dimension(400, 30));
        stockTextField.setBackground(color2);

        priceTextField = new JTextField("Price");
        priceTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        priceTextField.setPreferredSize(new Dimension(400, 30));
        priceTextField.setBackground(color2);

        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        updateButton.setBackground(color2);
        updateButton.setForeground(Color.WHITE);
        updateButton.setPreferredSize(new Dimension(100, 40));
        updateButton.setBorder(BorderFactory.createLineBorder(Color.black));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductBLL productBLL = new ProductBLL();
                Product pd = new Product(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Integer.parseInt(stockTextField.getText()), Double.parseDouble(priceTextField.getText()));
                productBLL.updateProductById(pd,Integer.parseInt(idTextField.getText()));
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(idTextField);
        frame.add(editButton);
        frame.add(nameTextField);
        frame.add(stockTextField);
        frame.add(priceTextField);
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
            ProductBLL productBLL = new ProductBLL();
            Product pd = productBLL.findProductById(id);
            nameTextField.setText(pd.getName());
            stockTextField.setText(Integer.toString(pd.getStock()));
            priceTextField.setText(Double.toString(pd.getPrice()));
        }
    }
}
