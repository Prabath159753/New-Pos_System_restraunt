package lk.intelleon.dao.custom;

import lk.intelleon.dao.CrudDAO;
import lk.intelleon.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:27 PM
 **/

public interface CustomerDAO extends CrudDAO<Customer,String> {

    ResultSet generateCustomerId() throws SQLException, ClassNotFoundException;

    boolean ifCustomerIdExist(String id) throws SQLException, ClassNotFoundException;

    boolean ifCustomerTpExist(int tp) throws SQLException, ClassNotFoundException;

    Customer searchCustomerTp(int tp) throws SQLException, ClassNotFoundException;

}
