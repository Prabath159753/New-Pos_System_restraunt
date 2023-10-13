package lk.intelleon.dto.tm;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:17 PM
 **/

public class OrdersWiseCusTM {
    private String cusId;
    private String orderId;
    private Double totalCost;
    private String paymentMethod;

    public OrdersWiseCusTM() {
    }

    public OrdersWiseCusTM(String cusId, String orderId, Double totalCost, String paymentMethod) {
        this.cusId = cusId;
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.paymentMethod = paymentMethod;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrdersWiseCusTM{" +
                "cusId='" + cusId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", totalCost=" + totalCost +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
