package model.product;

import model.company.Company;

public class Microphone extends ElectronicProduct{
    protected int sensibility;
    protected int outputImpedance;
    protected String inputType;

    public Microphone() {
    }

    public Microphone(int id, String name, double price, int producer, String connection, String color, int warranty, int weight, int sensibility, int outputImpedance, String inputType) {
        super(id, name, price, producer, connection, color, warranty, weight);
        this.sensibility = sensibility;
        this.outputImpedance = outputImpedance;
        this.inputType = inputType;
    }

    public int getSensibility() {
        return sensibility;
    }

    public void setSensibility(int sensibility) {
        this.sensibility = sensibility;
    }

    public int getOutputImpedance() {
        return outputImpedance;
    }

    public void setOutputImpedance(int outputImpedance) {
        this.outputImpedance = outputImpedance;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
