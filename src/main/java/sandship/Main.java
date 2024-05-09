package sandship;

import sandship.model.Material;
import sandship.observer.WarehouseManager;
import sandship.observer.WarehouseObserver;
import sandship.observer.WarehouseSubject;
import sandship.service.AddMaterialException;
import sandship.service.IWarehouse;
import sandship.service.TransferException;
import sandship.service.Warehouse;

public class Main {
    public static void main(String[] args) {
        IWarehouse warehouse1 = new Warehouse("Warehouse 1");
        IWarehouse warehouse2 = new Warehouse("Warehouse 2");

        Material steel = new Material("Steel", "A strong metal", "steel_icon.png", 100);
        Material copper = new Material("Copper", "A conductive metal", "copper_icon.png", 50);
        Material wood = new Material("Wood", "Used for building", "wood_icon.png", 150);
        WarehouseObserver manager1 = new WarehouseManager("Manager 1");
        WarehouseObserver manager2 = new WarehouseManager("Manager 2");

        ((WarehouseSubject) warehouse1).addObserver(manager1);
        ((WarehouseSubject) warehouse1).addObserver(manager2);
        ((WarehouseSubject) warehouse2).addObserver(manager1);


        try {
            steel.setQuantity(40);
            warehouse1.store(steel);

            copper.setQuantity(30);
            warehouse1.store(copper);

            wood.setQuantity(100);
            warehouse1.store(wood);

        } catch (AddMaterialException e) {
            System.err.println("Error adding material to Warehouse 1: " + e.getMessage());
        }

        warehouse1.printMaterials();

        try {
            Material iron = new Material("Iron", "A strong metal", "iron_icon.png", 200);
            iron.setQuantity(80);
            warehouse2.store(iron);
        } catch (AddMaterialException e) {
            System.err.println("Error adding material to Warehouse 2: " + e.getMessage());
        }

        warehouse2.printMaterials();

        try {
            warehouse1.transfer("Copper", 20, warehouse2);
        } catch (TransferException e) {
            System.err.println("Error transferring Copper from Warehouse 1 to Warehouse 2: " + e.getMessage());
        }

        System.out.println("\nAfter transferring Copper from Warehouse 1 to Warehouse 2:");
        warehouse1.printMaterials();

        warehouse2.printMaterials();

        warehouse2.removeByName("Iron");

        System.out.println("\nAfter removing Iron from Warehouse 2:");
        warehouse2.printMaterials();
    }
}
