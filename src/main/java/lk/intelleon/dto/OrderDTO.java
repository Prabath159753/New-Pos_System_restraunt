package lk.intelleon.dto;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:56 PM
 **/

public class OrderDTO {
    private String orderId;
    private String dateTime;
    private Double total;
    private String cusId;
    private String  userId;
    private String paymentMethod;
    private String orderType;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String dateTime, Double total, String cusId, String userId, String paymentMethod, String orderType) {
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.total = total;
        this.cusId = cusId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", total=" + total +
                ", cusId='" + cusId + '\'' +
                ", userId='" + userId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
