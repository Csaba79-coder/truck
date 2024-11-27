package first;

import first.model.Item;
import first.model.Truck;
import first.service.TruckLoadOptimizer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // ez nem optimalizál, csak véletlen elhelyez
    public static void main(String[] args) {
        // Árulista létrehozása
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 10, 15, false));  // Az első áru (nem törékeny)
        items.add(new Item(2, 5, 8, true));    // A második áru (törékeny)

        // Teherautó létrehozása (pl. 100 térfogatú, 500 kg teherbírású)
        Truck truck = new Truck(10, 5, 100, 500);

        // Pakolás optimalizálása (döntési változók)
        int[][][] x_ijk = new int[items.size()][truck.getLength()][truck.getWidth()];
        x_ijk[0][1][1] = 1;  // Az első áru elhelyezése (1. pozíció)
        x_ijk[1][2][2] = 1;  // A második áru elhelyezése (2. pozíció)

        TruckLoadOptimizer optimizer = new TruckLoadOptimizer(items, truck);
        optimizer.optimizeTruckLoad(x_ijk);

        // Kiírjuk az eredményeket
        optimizer.printPlacement();
    }
}
