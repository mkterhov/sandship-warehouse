package sandship.observer;

import sandship.model.Material;

public interface WarehouseSubject {
    void addObserver(WarehouseObserver observer);

    void removeObserver(WarehouseObserver observer);

    void notifyObservers(OperationType operation, Material material, String message);
}
