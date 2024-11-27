package second;

import java.util.*;
// A rétegek nem lehetnek üresek, kivéve, ha nincsenek más áruk, amelyek azokba a rétegekbe kerülnek. Az áruk egymásra rakhatók, de a megfelelő súlykorlátozásokat és törékeny árukra vonatkozó szabályokat is figyelembe kell venni
// Az optimális térkihasználás érdekében az áruknak lehetőség szerint az üres helyekre kell kerülniük, figyelembe véve a térfogatot, súlyt és a speciális igényeket (pl. törékeny áruk, hűtést igénylő áruk).
// Nincs üres réteg a rétegek között: Ha egy rétegben van hely, az azt jelenti, hogy a réteg többi pozíciója is felhasználásra kerül, és a következő rétegben is helyet kell biztosítani az áruk számára.
// Szoros kapcsolat a rétegek között: Ha egy rétegben elhelyezünk egy árut, akkor a következő rétegben is figyelembe kell venni, hogy az áruk hogyan helyezkedhetnek el. Az üres rétegek nem lehetnek az optimális elrendezés részei.
// Áruk elhelyezése egymásra: Az áruknak a rétegeken belül kell helyet találniuk, és azokat egymásra is lehet rakni, ha az nem sérti a súly- és méretkorlátozásokat.
//  Ha a réteg tele van, akkor az új árut a következő rétegbe kellene tenni, és a programot úgy kell módosítani, hogy ne hagyja ki a rétegeket.
public class TruckLoadOptimizerNotWorkingProperOfLayer {

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

        // Ellenőrizzük, hogy egy réteg tele van-e
        public boolean isLayerFull(int layer) {
            for (int x = 0; x < layers[0].length; x++) {
                for (int y = 0; y < layers[0][0].length; y++) {
                    if (layers[layer][x][y] == 0) {
                        return false; // Ha van üres hely, akkor a réteg nincs tele
                    }
                }
            }
            return true;
        }

        // Ellenőrizzük, hogy minden réteg tele van-e
        public boolean isAllLayersFull() {
            for (int layer = 0; layer < layers.length; layer++) {
                if (!isLayerFull(layer)) {
                    return false;
                }
            }
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
                if (truck.isLayerFull(layer)) {
                    System.out.println("A " + layer + ". réteg már tele van.");
                    continue; // Ha a réteg tele van, próbálkozunk a következő réteggel
                }

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
