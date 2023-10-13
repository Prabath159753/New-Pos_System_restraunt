package lk.intelleon.bo.custom;

import lk.intelleon.bo.SuperBO;
import lk.intelleon.dto.RefDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:43 PM
 **/

public interface RefBO extends SuperBO {

    boolean addRef( RefDTO refDTO) throws Exception ;

    boolean deleteRef(String id) throws Exception ;

    RefDTO searchRef(String id) throws Exception ;

    boolean updateRef(RefDTO refDTO) throws Exception ;

    ArrayList<RefDTO> getAllRefs() throws Exception ;

    String generateRefId() throws SQLException, ClassNotFoundException;

}
