package lk.intelleon.dao.custom.impl;

import lk.intelleon.dao.custom.OrderDAO;
import lk.intelleon.entity.Order;
import lk.intelleon.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:33 PM
 **/

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws Exception {
        return CrudUtil.execute("INSERT INTO Orders VALUES (?,?,?,?,?,?,?)",
                order.getOrderId(),order.getDateTime(),
                order.getTotal(),order.getCusId(),
                order.getUserId(),order.getPaymentMethod(),order.getOrderType());
    }

    @Override
    public boolean update(Order order) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute( "DELETE FROM Orders WHERE id=? ",s );
    }

    @Override
    public Order search(String s) throws Exception {
        ResultSet resultSet = CrudUtil.execute( "SELECT * FROM Orders WHERE id=?" , s );

        while (resultSet.next()){
            Order order = new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            return order;
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute( "SELECT * FROM Orders" );

        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()){
            Order order= new Order(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public ResultSet generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id FROM Orders Order By id DESC LIMIT 1");
        return resultSet;
    }

    @Override
    public ArrayList<Order> getAllOrdersByCusId(String cusId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders WHERE customerId=?", cusId);

        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()){
            Order order= new Order(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrderByDaily(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders WHERE date=?",date);
        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()){
            Order order= new Order(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrderByMonthly(String today, String afterMonth) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders WHERE date BETWEEN ? AND ?",today,afterMonth);
        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()){
            Order order= new Order(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrdersByPaymentMethod(String paymentMethod) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders WHERE paymentMethod=?", paymentMethod);

        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()){
            Order order= new Order(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            orders.add(order);
        }
        return orders;
    }

}
