package lk.intelleon.bo.custom.impl;

import lk.intelleon.bo.custom.OrderBO;
import lk.intelleon.dao.DAOFactory;
import lk.intelleon.dao.custom.OrderDAO;
import lk.intelleon.dto.OrderDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:46 PM
 **/

public class PlaceOrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getSuperDAO(DAOFactory.DAOType.ORDER);

    @Override
    public boolean purchaseOrder(OrderDTO orders) throws Exception {
        return false;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = orderDAO.generateOrderId();

        if (resultSet.next()){
            int tempId = Integer.parseInt( resultSet.getString( 1 ).split( "O" )[ 1 ] );
            tempId+=1;
            if (tempId < 10){
                return "O00"+tempId;
            }else if (tempId<100){
                return "O0"+tempId;
            }
        }
        return "O001";
    }

}
