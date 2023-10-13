package lk.intelleon.bo.custom;

import lk.intelleon.bo.SuperBO;
import lk.intelleon.dto.OrderDTO;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:42 PM
 **/

public interface OrderBO extends SuperBO {

    boolean purchaseOrder( OrderDTO orders) throws Exception;

    String generateOrderId() throws SQLException, ClassNotFoundException;

}
