package lk.intelleon.bo.custom;

import lk.intelleon.bo.SuperBO;
import lk.intelleon.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:42 PM
 **/

public interface CustomerBO extends SuperBO {

    boolean addCustomer( CustomerDTO customer) throws Exception ;

    boolean deleteCustomer(String id) throws Exception ;

    CustomerDTO searchCustomer(String id) throws Exception ;

    boolean updateCustomer(CustomerDTO customer) throws Exception ;

    ArrayList<CustomerDTO> getAllCustomer() throws Exception ;

    String generateCustomerId() throws SQLException, ClassNotFoundException;

    boolean ifCustomerIdExist(String id) throws SQLException, ClassNotFoundException;

    boolean ifCustomerTpExist(int tp) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomerTp(int tp) throws SQLException, ClassNotFoundException;

}
