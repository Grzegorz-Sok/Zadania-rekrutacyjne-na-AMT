import java.util.ArrayList;
import java.util.List;

public class OneSale {

    private String productGroup;
    private String payment;
    private String region;
    private List<Double> saleList = new ArrayList<>();

    public OneSale() {
    }

    public OneSale(List<String> list, Double saleValue) {
        this.productGroup = list.get(0);
        this.payment = list.get(1);
        this.region = list.get(2);
        this.saleList.add(saleValue);
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Double> getSaleList() {
        return saleList;
    }

    public void addSaleValue(double saleValue) {
        this.saleList.add(saleValue);
    }
}
