package lk.intelleon.dao;

import lk.intelleon.dao.custom.impl.*;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:24 PM
 **/

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance(){
        if (daoFactory==null){
            daoFactory = new DAOFactory();
            return daoFactory;
        }
        return daoFactory;
    }

    public enum DAOType{
        CUSTOMER,PRODUCT,USER,ORDER,ORDERDETAIL,REF
    }

    public SuperDAO getSuperDAO(DAOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case USER:
                return new UserDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailsDAOImpl();
            case REF:
                return new RefDAOImpl();
            default:
                return null;
        }
    }
}
