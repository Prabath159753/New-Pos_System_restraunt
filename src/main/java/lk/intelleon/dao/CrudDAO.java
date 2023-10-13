package lk.intelleon.dao;

import java.util.List;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:25 PM
 **/

public interface CrudDAO <T,ID> extends SuperDAO{

    boolean add ( T t ) throws Exception;

    boolean update ( T t ) throws Exception;

    boolean delete ( ID id ) throws Exception;

    T search ( ID id ) throws Exception;

    List< T > getAll ( ) throws Exception;

}
