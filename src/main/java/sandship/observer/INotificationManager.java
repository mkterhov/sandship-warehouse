package sandship.observer;

public interface INotificationManager {
    void addObserver(WarehouseObserver observer);

    void removeObserver(WarehouseObserver observer);

    void notifyObservers(String message);
}
