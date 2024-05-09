package sandship.observer;

import sandship.model.Material;

public interface WarehouseObserver {
    void update(OperationType operation, Material material, String message);
}
