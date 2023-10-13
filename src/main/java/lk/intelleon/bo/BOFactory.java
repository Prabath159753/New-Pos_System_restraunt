package lk.intelleon.bo;

import lk.intelleon.bo.custom.impl.*;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:40 PM
 **/

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance(){
        if (boFactory==null){
            boFactory = new BOFactory();
            return boFactory;
        }
        return boFactory;
    }

    public enum BOType{
        CUSTOMER,PRODUCT,USER,ORDER,ORDERDETAIL,SYSTEMREPORT,REF
    }

    public SuperBO getBO(BOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case USER:
                return new UserBOImpl();
            case ORDER:
                return new PlaceOrderBOImpl();
            case SYSTEMREPORT:
                return new SystemReportBOImpl();
            case REF:
                return new RefBOImpl();
            default:
                return null;
        }
    }

}
