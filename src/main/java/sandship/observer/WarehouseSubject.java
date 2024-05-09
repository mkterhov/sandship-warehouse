package sandship.observer;

import sandship.model.Material;

public interface WarehouseSubject {
    public void addObserver(WarehouseObserver observer);

    public void removeObserver(WarehouseObserver observer);

    public void notifyObservers(OperationType operation, Material material, String message);
}
