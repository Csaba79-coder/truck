package second;

import java.util.ArrayList;
import java.util.List;

// az áruk elhelyezése a rétegek és pozíciók figyelembevételével történik
// a törékeny áruk csak a legfelső rétegre kerülhetnek, míg a hűtést igénylő áruk csak egy hűtött rétegre kerülhetnek
// heurisztikus módszer, tehát nem biztos, hogy globálisan optimális, de figyelembe veszi a fizikai elhelyezés és a korlátozásokat
public class TruckLoadOptimizerFirst {

    private static final int MAX_LAYERS = 3; // Maximális rétegszám

    // Áru osztály
    static class Item {
        int id;
        double volume;
        double weight;
        boolean isFragile;
        boolean needsCooling;

        public Item(int id, double volume, double weight, boolean isFragile, boolean needsCooling) {
            this.id = id;
            this.volume = volume;
            this.weight = weight;
            this.isFragile = isFragile;
            this.needsCooling = needsCooling;
        }
    }

    // Teherautó osztály
    static class Truck {
        double totalVolume;
        double maxWeight;
        double[][][] layers; // Rétegek: [réteg][hossz][szélesség]

        double currentVolumeUsed = 0;
        double currentWeightUsed = 0;

        public Truck(int length, int width, int maxLayers, double totalVolume, double maxWeight) {
            this.totalVolume = totalVolume;
            this.maxWeight = maxWeight;
            this.layers = new double[maxLayers][length][width];
        }

        // Ellenőrizhető, hogy egy adott hely szabad-e
        public boolean canPlaceItem(Item item, int layer, int x, int y) {
            // Méretellenőrzés
            if (currentVolumeUsed + item.volume > totalVolume) return false;
            if (currentWeightUsed + item.weight > maxWeight) return false;

            // Törékenység ellenőrzése
            if (item.isFragile && layer < layers.length - 1) return false; // Törékeny áru nem lehet az alatta lévő rétegekben

            // Hűtést igénylő áruk
            if (item.needsCooling && layer < layers.length - 1) return false; // Hűtést igénylő áru csak a hűtött rétegben lehet

            // Rétegellenőrzés: csak az üres helyet engedjük meg
            if (layers[layer][x][y] > 0) return false; // Ahelyett, hogy az üres helyet fogadnánk el, most azt ellenőrizzük, hogy nincs más ott

            return true;
        }

        // Áru elhelyezése
        public boolean placeItem(Item item, int layer, int x, int y) {
            if (!canPlaceItem(item, layer, x, y)) return false;
            layers[layer][x][y] = item.volume;
            currentVolumeUsed += item.volume;
            currentWeightUsed += item.weight;
            return true;
        }
    }

    // Optimalizáló algoritmus
    public static void optimizeTruckLoad(List<Item> items, Truck truck) {
        // Áruk rendezése térfogat szerint csökkenő sorrendben
        items.sort((a, b) -> Double.compare(b.volume, a.volume));

        for (Item item : items) {
            boolean placed = false;

            // Rétegek és pozíciók bejárása
            for (int layer = 0; layer < MAX_LAYERS && !placed; layer++) {
                for (int x = 0; x < truck.layers[0].length && !placed; x++) {
                    for (int y = 0; y < truck.layers[0][0].length && !placed; y++) {
                        if (truck.placeItem(item, layer, x, y)) {
                            placed = true;
                            System.out.println("Áru " + item.id + " elhelyezve a(z) " + layer + ". rétegben (" + x + ", " + y + ")");
                        }
                    }
                }
            }

            if (!placed) {
                System.out.println("Áru " + item.id + " nem helyezhető el!");
            }
        }
    }

    // Tesztelés
    public static void main(String[] args) {
        // Teherautó inicializálása
        Truck truck = new Truck(5, 5, MAX_LAYERS, 100, 500);

        // Áruk inicializálása
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 10, 50, false, false)); // Nem törékeny
        items.add(new Item(2, 5, 20, true, false));  // Törékeny
        items.add(new Item(3, 8, 30, false, true));  // Hűtést igényel

        // Optimalizálás
        optimizeTruckLoad(items, truck);
    }
}
