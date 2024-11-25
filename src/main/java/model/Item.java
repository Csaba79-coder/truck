package model;

// 1. Áru osztály (model.Item)
public class Item {

    private int id;
    private double volume;  // Az áru térfogata
    private double weight;  // Az áru súlya

    private boolean isFragile;  // Törékeny-e az áru?

    public Item(int id, double volume, double weight, boolean isFragile) {
        this.id = id;
        this.volume = volume;
        this.weight = weight;
        this.isFragile = isFragile;
    }

    public int getId() {
        return id;
    }

    public double getVolume() {
        return volume;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }
}