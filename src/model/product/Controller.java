package model.product;

import model.company.Company;

public class Controller extends ElectronicProduct{
    protected int buttonCount;
    protected String batteryType;
    protected double batteryTime;
    protected String os;

    public Controller() {
    }

    public Controller(int id, String name, double price, int producer, String connection, String color, int warranty, int weight, int buttonCount, String batteryType, double batteryTime, String os) {
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

    public double getBatteryTime() {
        return batteryTime;
    }

    public void setBatteryTime(double batteryTime) {
        this.batteryTime = batteryTime;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
