package lk.intelleon.dao.custom;

import lk.intelleon.dao.CrudDAO;
import lk.intelleon.entity.Ref;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:29 PM
 **/

public interface RefDAO extends CrudDAO<Ref,String> {

    ResultSet generateRefId() throws SQLException, ClassNotFoundException;

}
