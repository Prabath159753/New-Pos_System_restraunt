package lk.intelleon.dao.custom;

import lk.intelleon.dao.CrudDAO;
import lk.intelleon.entity.OrderDetail;

import java.util.List;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:28 PM
 **/

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {

    List<OrderDetail> searchByOrderId(String s) throws Exception;

}
