package lk.intelleon.dto;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:58 PM
 **/

public class TempDataDTO {
    private String orderId;
    private String orderDate;
    private String cusId;
    private String cusType;
    private String cusName;
    private String cusAddress;
    private String cusCity;
    private String cusProvince;
    private int cusContact;
    private String cashierId;
    private Double subTot;
    private String refId;
    private String paymentMethod;
    private String orderType;

    public TempDataDTO() {
    }

    public TempDataDTO(String orderId, String orderDate, String cusId, String cusType, String cusName, String cusAddress, String cusCity, String cusProvince, int cusContact, String cashierId, Double subTot, String refId, String paymentMethod, String orderType) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cusId = cusId;
        this.cusType = cusType;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusCity = cusCity;
        this.cusProvince = cusProvince;
        this.cusContact = cusContact;
        this.cashierId = cashierId;
        this.subTot = subTot;
        this.refId = refId;
        this.paymentMethod = paymentMethod;
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusCity() {
        return cusCity;
    }

    public void setCusCity(String cusCity) {
        this.cusCity = cusCity;
    }

    public String getCusProvince() {
        return cusProvince;
    }

    public void setCusProvince(String cusProvince) {
        this.cusProvince = cusProvince;
    }

    public int getCusContact() {
        return cusContact;
    }

    public void setCusContact(int cusContact) {
        this.cusContact = cusContact;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public Double getSubTot() {
        return subTot;
    }

    public void setSubTot(Double subTot) {
        this.subTot = subTot;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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
        return "TempDataDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cusId='" + cusId + '\'' +
                ", cusType='" + cusType + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusCity='" + cusCity + '\'' +
                ", cusProvince='" + cusProvince + '\'' +
                ", cusContact=" + cusContact +
                ", cashierId='" + cashierId + '\'' +
                ", subTot=" + subTot +
                ", refId='" + refId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
