package sandship.observer;

import sandship.model.Material;

public class WarehouseManager implements WarehouseObserver {
    private final String name;

    public WarehouseManager(String name) {
        this.name = name;
    }

    @Override
    public void update(OperationType operation, Material material, String message) {
        System.out.printf(
                "%s received notification (%s): %s\n",
                name, operation, message
        );
    }
}
