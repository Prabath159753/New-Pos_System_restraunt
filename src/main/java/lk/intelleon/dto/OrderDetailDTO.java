package lk.intelleon.dto;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:55 PM
 **/

public class OrderDetailDTO {
    private int qty;
    private Double unitPrice;
    private Double disCount;
    private String orderId;
    private String productId;
    private String productName;
    private String refId;
    private Double amount;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int qty, Double unitPrice, Double disCount, String orderId, String productId, String productName, String refId, Double amount) {
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.disCount = disCount;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        return "OrderDetailDTO{" +
                "qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", disCount=" + disCount +
                ", orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", refId='" + refId + '\'' +
                ", amount=" + amount +
                '}';
    }

}
