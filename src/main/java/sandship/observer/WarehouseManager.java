package sandship.observer;

import sandship.model.Material;

public class WarehouseManager implements WarehouseObserver {
    private final String name;

    public WarehouseManager(String name) {
        this.name = name;
    }

    @Override
    public void update(OperationType operation, Material material, String message) {
        System.out.println(name + " received notification (" + operation + "): " + message +
                " - Material: " + material.getName() +
                ", Quantity: " + material.getQuantity() +
                ", Max Capacity: " + material.getMaxCapacity());
    }
}
