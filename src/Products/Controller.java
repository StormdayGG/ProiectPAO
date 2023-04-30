package Products;

import Companies.Company;

public class Controller extends ElectronicProduct{
    protected int buttonCount;
    protected String batteryType;
    protected float batteryTime;
    protected String os;

    public Controller() {
    }

    public Controller(int id, String name, float price, Company producer, String connection, String color, int warranty, int weight, int buttonCount, String batteryType, float batteryTime, String os) {
        super(id, name, price, producer, connection, color, warranty, weight);
        this.buttonCount = buttonCount;
        this.batteryType = batteryType;
        this.batteryTime = batteryTime;
        this.os = os;
    }

    public int getButtonCount() {
        return buttonCount;
    }

    public void setButtonCount(int buttonCount) {
        this.buttonCount = buttonCount;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public float getBatteryTime() {
        return batteryTime;
    }

    public void setBatteryTime(float batteryTime) {
        this.batteryTime = batteryTime;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
