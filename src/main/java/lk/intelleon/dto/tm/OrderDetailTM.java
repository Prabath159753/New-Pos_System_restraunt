package lk.intelleon.dto.tm;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:16 PM
 **/

public class OrderDetailTM {
    private int qty;
    private Double unitPrice;
    private Double disCount;
    private String orderId;
    private String propertyId;
    private String refId;
    private Double amount;

    public OrderDetailTM ( ) {
    }

    public OrderDetailTM(int qty, Double unitPrice, Double disCount, String orderId, String propertyId, String refId, Double amount) {
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.disCount = disCount;
        this.orderId = orderId;
        this.propertyId = propertyId;
        this.refId = refId;
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDisCount() {
        return disCount;
    }

    public void setDisCount(Double disCount) {
        this.disCount = disCount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", disCount=" + disCount +
                ", orderId='" + orderId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", refId='" + refId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
