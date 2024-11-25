package model;

// 2. Teherautó osztály (model.Truck)
public class Truck {

    private int length;
    private int width;
    private double totalVolume;  // Teherautó teljes térfogata
    private double maxWeight;  // Maximális teherbírás
    private double currentVolumeUsed;  // Használt térfogat
    private double currentWeightUsed;  // Használt súly

    public Truck(int length, int width, double totalVolume, double maxWeight) {
        this.length = length;
        this.width = width;
        this.totalVolume = totalVolume;
        this.maxWeight = maxWeight;
        this.currentVolumeUsed = 0;
        this.currentWeightUsed = 0;
    }

    // Hozzáadjuk az áru térfogatát és súlyát a teherautóhoz
    public boolean addItem(Item item) {
        if (currentVolumeUsed + item.getVolume() <= totalVolume && currentWeightUsed + item.getWeight() <= maxWeight) {
            currentVolumeUsed += item.getVolume();
            currentWeightUsed += item.getWeight();
            return true;
        }
        return false;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentVolumeUsed() {
        return currentVolumeUsed;
    }

    public double getCurrentWeightUsed() {
        return currentWeightUsed;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setCurrentVolumeUsed(double currentVolumeUsed) {
        this.currentVolumeUsed = currentVolumeUsed;
    }

    public void setCurrentWeightUsed(double currentWeightUsed) {
        this.currentWeightUsed = currentWeightUsed;
    }
}
