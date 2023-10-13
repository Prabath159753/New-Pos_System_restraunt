package lk.intelleon.entity;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:04 PM
 **/

public class Ref {
    private String refId;
    private String name;
    private String idNumber;
    private int tel;
    private String address;

    public Ref() {
    }

    public Ref(String refId, String name, String idNumber, int tel, String address) {
        this.refId = refId;
        this.name = name;
        this.idNumber = idNumber;
        this.tel = tel;
        this.address = address;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Ref{" +
                "refId='" + refId + '\'' +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", tel=" + tel +
                ", address='" + address + '\'' +
                '}';
    }
}
