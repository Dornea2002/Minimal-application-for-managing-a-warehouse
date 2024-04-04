package Presentation;

import Application.Reflection;
import BusinessLogic.ProductBLL;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManipulation extends JFrame {
    JButton addNewProductButton;
    JButton editProductButton;
    JButton deleteProductButton;
    JButton viewProductButton;

    public ProductManipulation(){
        ProductBLL productBLL = new ProductBLL();
        GridLayout layout = new GridLayout(4,1,30,30);

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        List<Product> list = null;

        addNewProductButton = new JButton("Add new product");
        addNewProductButton.setBackground(color2);
        addNewProductButton.setForeground(Color.WHITE);
        addNewProductButton.setSize(200,50);
        addNewProductButton.setFont(new Font("Calibri",Font.BOLD,20));
        addNewProductButton.setBorder(BorderFactory.createLineBorder(Color.black));
        addNewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addNewProductButton) {
                    JFrame frame = new JFrame("Add product");

                    JTextField  nameTextField= new JTextField("Name");
                    nameTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
                    nameTextField.setPreferredSize(new Dimension(400, 30));
                    nameTextField.setBackground(color2);


                    JTextField stockTextField = new JTextField("Stock");
                    stockTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
                    stockTextField.setPreferredSize(new Dimension(400, 30));
                    stockTextField.setBackground(color2);

                    JTextField priceTextField = new JTextField("Price");
                    priceTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
                    priceTextField.setPreferredSize(new Dimension(400, 30));
                    priceTextField.setBackground(color2);

                    JButton addButton = new JButton("Add");
                    addButton.setFont(new Font("Consolas", Font.PLAIN, 15));
                    addButton.setBackground(color2);
                    addButton.setForeground(Color.WHITE);
                    addButton.setPreferredSize(new Dimension(100, 40));
                    addButton.setBorder(BorderFactory.createLineBorder(Color.black));
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Product pd = new Product(nameTextField.getText(), Integer.parseInt(stockTextField.getText()), Double.parseDouble(priceTextField.getText()));
                            productBLL.insertProduct(pd);
                        }
                    });

                    frame.setLayout(new FlowLayout());
                    frame.add(nameTextField);
                    frame.add(stockTextField);
                    frame.add(priceTextField);
                    frame.add(addButton);
                    frame.setSize(500, 250);
                    frame.getContentPane().setBackground(color1);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }
        });

        editProductButton = new JButton("Edit product");
        editProductButton.setBackground(color2);
        editProductButton.setForeground(Color.WHITE);
        editProductButton.setSize(200,50);
        editProductButton.setFont(new Font("Calibri",Font.BOLD,20));
        editProductButton.setBorder(BorderFactory.createLineBorder(Color.black));
        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == editProductButton) {
                    new EditProduct();
                }
            }
        });

        deleteProductButton = new JButton("Delete product");
        deleteProductButton.setBackground(color2);
        deleteProductButton.setForeground(Color.WHITE);
        deleteProductButton.setSize(200,50);
        deleteProductButton.setFont(new Font("Calibri",Font.BOLD,20));
        deleteProductButton.setBorder(BorderFactory.createLineBorder(Color.black));
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == deleteProductButton){
                    JFrame frame = new JFrame("Delete product");

                    JTextField nameTextField = new JTextField("Name");
                    nameTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
                    nameTextField.setPreferredSize(new Dimension(400, 30));
                    nameTextField.setBackground(color2);

                    JButton deleteButton = new JButton("Delete");
                    deleteButton.setFont(new Font("Consolas", Font.PLAIN, 15));
                    deleteButton.setBackground(color2);
                    deleteButton.setForeground(Color.WHITE);
                    deleteButton.setPreferredSize(new Dimension(100, 40));
                    deleteButton.setBorder(BorderFactory.createLineBorder(Color.black));
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            productBLL.deleteProduct(nameTextField.getText());
                        }
                    });

                    frame.setLayout(new FlowLayout());
                    frame.add(nameTextField);
                    frame.add(deleteButton);
                    frame.setSize(500, 140);
                    frame.getContentPane().setBackground(color1);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }
        });

        viewProductButton= new JButton("View products");
        viewProductButton.setBackground(color2);
        viewProductButton.setForeground(Color.WHITE);
        viewProductButton.setSize(200,50);
        viewProductButton.setFont(new Font("Calibri",Font.BOLD,20));
        viewProductButton.setBorder(BorderFactory.createLineBorder(Color.black));
        viewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("View products");
                List<Product> products = productBLL.findAllProducts();
                List<String> fieldsList = Reflection.getFields(products.get(0));
                String[] fields = new String[fieldsList.size()];
                int i = 0;
                for(String field:fieldsList){
                    fields[i++] = field;
                }

                Object[][] data = new Object[products.size()][fieldsList.size()];
                i=0;
                for(Product pd:products){
                    ArrayList<Object> obj = Reflection.getValues(pd);
                    int j=0;
                    for(Object o : obj){
                        data[i][j++] = o;
                    }
                    i++;
                }

                JTable table = new JTable(data, fields);
                table.setPreferredSize(new Dimension(600,400));
                table.setBackground(color2);

                JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);
                scrollPane.setBackground(color2);

                frame.add(scrollPane);

                frame.setLayout(new FlowLayout());
                frame.setSize(700, 500);
                frame.getContentPane().setBackground(color1);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });

        this.setLayout(layout);
        this.add(addNewProductButton);
        this.add(editProductButton);
        this.add(deleteProductButton);
        this.add(viewProductButton);
        this.setSize(400,400);
        this.setTitle("Product Page");
        this.getContentPane().setBackground(color1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}
