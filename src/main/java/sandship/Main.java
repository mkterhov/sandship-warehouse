package sandship;

import sandship.model.Material;
import sandship.observer.WarehouseManager;
import sandship.observer.WarehouseObserver;
import sandship.observer.WarehouseSubject;
import sandship.service.AddMaterialException;
import sandship.service.IWarehouse;
import sandship.service.TransferException;
import sandship.service.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final int NUM_WAREHOUSES = 100;
    private static final int NUM_THREADS = 10;
    private static final int OPERATIONS_PER_THREAD = 100;
    private static final int INITIAL_QUANTITY = 500;
    private static final int MAX_CAPACITY = 1000;

    public static void main(String[] args) throws InterruptedException {
        //uncomment below to run single threaded test
        test();

        //uncomment below to run concurrent test
        //testConcurrent();
    }

    private static void test() {
        IWarehouse warehouse1 = new Warehouse("Warehouse 1");
        IWarehouse warehouse2 = new Warehouse("Warehouse 2");

        Material steel = new Material.Builder("Steel", "A strong metal", "steel_icon.png", 100)
                .quantity(40)
                .build();
        Material copper = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 50)
                .quantity(30)
                .build();
        Material wood = new Material.Builder("Wood", "Used for building", "wood_icon.png", 150)
                .quantity(100)
                .build();

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
            Material iron = new Material.Builder("Iron", "A strong metal", "iron_icon.png", 200)
                    .quantity(80)
                    .build();
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

        warehouse2.remove("Iron");

        System.out.println("\nAfter removing Iron from Warehouse 2:");
        warehouse2.printMaterials();
    }

    public static void testConcurrent() throws InterruptedException {
        List<Warehouse> warehouses = new ArrayList<>();
        for (int i = 0; i < NUM_WAREHOUSES; i++) {
            warehouses.add(new Warehouse(UUID.randomUUID().toString()));
        }

        // Add initial materials to all warehouses
        for (Warehouse warehouse : warehouses) {
            try {
                warehouse.store(new Material.Builder("Iron", "A piece of iron", "iron.png", MAX_CAPACITY).quantity(INITIAL_QUANTITY).build());
                warehouse.store(new Material.Builder("Copper", "A piece of copper", "copper.png", MAX_CAPACITY).quantity(INITIAL_QUANTITY).build());
                warehouse.store(new Material.Builder("Bolt", "A bolt", "bolt.png", MAX_CAPACITY).quantity(INITIAL_QUANTITY).build());
            } catch (AddMaterialException e) {
                System.err.println(e.getMessage());
            }
        }

        WarehouseObserver manager1 = new WarehouseManager("Manager 1");
        for (Warehouse warehouse : warehouses) {
            warehouse.addObserver(manager1);
        }

        // Create a CountDownLatch to synchronize thread completion
        CountDownLatch latch = new CountDownLatch(NUM_THREADS);

        // Define tasks for the threads
        Runnable task = () -> {
            try {
                for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                    int fromIndex, toIndex;
                    do {
                        fromIndex = (int) (Math.random() * NUM_WAREHOUSES);
                        toIndex = (int) (Math.random() * NUM_WAREHOUSES);
                    } while (fromIndex == toIndex);

                    Warehouse fromWarehouse = warehouses.get(fromIndex);
                    Warehouse toWarehouse = warehouses.get(toIndex);

                    int materialType = (int) (Math.random() * 3);
                    String materialName;
                    int quantity = switch (materialType) {
                        case 0 -> {
                            materialName = "Iron";
                            yield 2 + (int) (Math.random() * 20);
                        }
                        case 1 -> {
                            materialName = "Copper";
                            yield 3 + (int) (Math.random() * 10);
                        }
                        default -> {
                            materialName = "Bolt";
                            yield 4 + (int) (Math.random() * 5);
                        }
                    };

                    try {
                        fromWarehouse.transfer(materialName, quantity, toWarehouse);
                    } catch (TransferException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } finally {
                latch.countDown();
            }
        };

        // Start 10 threads to perform operations concurrently
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }

        latch.await();

        int expectedIron = NUM_WAREHOUSES * INITIAL_QUANTITY;
        int expectedCopper = NUM_WAREHOUSES * INITIAL_QUANTITY;
        int expectedBolt = NUM_WAREHOUSES * INITIAL_QUANTITY;

        int actualIron = 0;
        int actualCopper = 0;
        int actualBolt = 0;

        for (Warehouse warehouse : warehouses) {
            Map<String, Material> materials = warehouse.getMaterials();
            actualIron += materials.get("Iron").getQuantity();
            actualCopper += materials.get("Copper").getQuantity();
            actualBolt += materials.get("Bolt").getQuantity();
        }

        boolean allPassed = true;

        if (expectedIron != actualIron) {
            System.err.println("Iron quantity mismatch: Expected " + expectedIron + ", but got " + actualIron);
            allPassed = false;
        }

        if (expectedCopper != actualCopper) {
            System.err.println("Copper quantity mismatch: Expected " + expectedCopper + ", but got " + actualCopper);
            allPassed = false;
        }

        if (expectedBolt != actualBolt) {
            System.err.println("Bolt quantity mismatch: Expected " + expectedBolt + ", but got " + actualBolt);
            allPassed = false;
        }

        if (allPassed) {
            System.out.println("All assertions passed!");
        } else {
            System.err.println("Some assertions failed.");
        }

        System.out.println("Finished!");

    }
}
