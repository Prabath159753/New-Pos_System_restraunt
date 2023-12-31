package lk.intelleon.dao.custom;

import lk.intelleon.dao.CrudDAO;
import lk.intelleon.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:27 PM
 **/

public interface OrderDAO extends CrudDAO<Order, String> {

    ResultSet generateOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<Order> getAllOrdersByCusId(String cusId) throws SQLException, ClassNotFoundException;

    ArrayList<Order> getAllOrderByDaily(String date) throws SQLException, ClassNotFoundException;

    ArrayList<Order> getAllOrderByMonthly(String today, String afterMonth) throws SQLException, ClassNotFoundException;

    ArrayList<Order> getAllOrdersByPaymentMethod(String paymentMethod) throws SQLException, ClassNotFoundException;

}
