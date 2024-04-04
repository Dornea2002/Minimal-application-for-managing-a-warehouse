package Presentation;

import Application.Reflection;
import BusinessLogic.OrderBLL;
import Model.Order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class ViewOrders{

    ViewOrders(){
        OrderBLL orderBLL = new OrderBLL();

        Color color1=new Color(254, 232, 176);
        Color color2=new Color(156, 167, 119);

        JFrame frame = new JFrame("View orders");
        List<Order> orders = orderBLL.findAllOrders();
        List<String> fieldsList = Reflection.getFields(orders.get(0));
        String[] fields = new String[fieldsList.size()];

        int i = 0;
        for(String field:fieldsList){
            fields[i++] = field;
        }

        Object[][] data = new Object[orders.size()][fieldsList.size()];
        i=0;
        for(Order order:orders){
            ArrayList<Object> obj = Reflection.getValues(order);
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
}
