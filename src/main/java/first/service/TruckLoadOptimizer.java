package first.service;

import first.model.Item;
import first.model.Truck;

import java.util.List;

// 3. Pakolás optimalizálása (first.model.service.TruckLoadOptimizer)
public class TruckLoadOptimizer {

    private List<Item> items;
    private Truck truck;
    private int[][][] placementMatrix;  // A mátrix, amely az áruk elhelyezését tárolja

    public TruckLoadOptimizer(List<Item> items, Truck truck) {
        this.items = items;
        this.truck = truck;
        this.placementMatrix = new int[items.size()][truck.getLength()][truck.getWidth()];
    }

    // A döntési változók alapján meghatározza az áruk elhelyezését
    public void optimizeTruckLoad(int[][][] x_ijk) {
        double totalVolumeUsed = 0;  // Az összes felhasznált térfogat

        // Iterálunk az összes árun és a teherautó pozícióira
        for (int i = 0; i < items.size(); i++) {  // Az áruk listája
            for (int j = 0; j < truck.getLength(); j++) {  // Teherautó hossza
                for (int k = 0; k < truck.getWidth(); k++) {  // Teherautó szélessége
                    if (x_ijk[i][j][k] == 1) {  // Ha a döntési változó értéke 1, azaz az áru ott van
                        placementMatrix[i][j][k] = 1;  // Beállítjuk a helyet a mátrixban

                        // Frissítjük a teherautó térfogatát és súlyát az áru alapján
                        if (!truck.addItem(items.get(i))) {
                            System.out.println("Hiba: Az áru nem fér el a teherautóban (túlterhelés)!");
                        }
                    }
                }
            }
        }
    }

    // Kiírjuk az elhelyezett áruk helyét és a használt kapacitást
    public void printPlacement() {
        System.out.println("Teherautó állapota:");
        System.out.println("Használt térfogat: " + truck.getCurrentVolumeUsed() + "/" + truck.getTotalVolume());
        System.out.println("Használt súly: " + truck.getCurrentWeightUsed() + "/" + truck.getMaxWeight());

        // Kiírjuk a döntési változók alapján a helyeket
        for (int i = 0; i < items.size(); i++) {
            System.out.print("Áru " + items.get(i).getId() + " elhelyezése: ");
            for (int j = 0; j < truck.getLength(); j++) {
                for (int k = 0; k < truck.getWidth(); k++) {
                    if (placementMatrix[i][j][k] == 1) {
                        System.out.print("Hossz: " + j + ", Szélesség: " + k + "; ");
                    }
                }
            }
            System.out.println();
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public int[][][] getPlacementMatrix() {
        return placementMatrix;
    }

    public void setPlacementMatrix(int[][][] placementMatrix) {
        this.placementMatrix = placementMatrix;
    }
}
