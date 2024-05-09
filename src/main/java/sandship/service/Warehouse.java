package sandship.service;

import sandship.model.Material;
import sandship.observer.OperationType;
import sandship.observer.WarehouseObserver;
import sandship.observer.WarehouseSubject;

import java.util.*;

public class Warehouse implements IWarehouse, WarehouseSubject {
    private final String id;
    private Map<String, Material> materials = new HashMap<>();
    private final List<WarehouseObserver> observers = new ArrayList<>();

    public Warehouse(String id) {
        this.id = id;
    }

    @Override
    public void store(Material material) throws AddMaterialException {
        if (!validateMaterialAddition(material)) {
            throw new AddMaterialException("Failed to add material " + material.getName() + ". Exceeds max capacity.");
        }

        materials.merge(
                material.getName(),
                material,
                (existingMaterial, newMaterial) -> {
                    existingMaterial.addQuantity(newMaterial.getQuantity());
                    newMaterial.setQuantity(0);
                    return existingMaterial;
                });

        notifyObservers(OperationType.ADD, material, "Added " + material.getQuantity() + " of " + material.getName() + " to " + id);
    }

    @Override
    public void remove(String name) {
        Material removed = materials.remove(name);
        if (removed != null) {
            notifyObservers(OperationType.REMOVE, removed, "Removed material " + name + " from " + id);
        }
    }

    @Override
    public void transfer(String name, int quantity, IWarehouse toWarehouse) throws TransferException {
        if (!materials.containsKey(name)) {
            throw new TransferException("The requested material not found in " + id + "!");
        }

        if (toWarehouse.getId().equals(id)) {
            throw new TransferException("Cannot transfer material " + name + " to the same warehouse " + id);
        }
        Material material = materials.get(name);
        Material transferredMaterial = new Material.Builder(
                material.getName(),
                material.getDescription(),
                material.getIcon(),
                material.getMaxCapacity()
        )
                .quantity(quantity)
                .build();

        try {
            toWarehouse.store(transferredMaterial);
        } catch (AddMaterialException e) {
            throw new TransferException("Failed to transfer " + quantity + " of " + name + " from " + id + " to " + toWarehouse.getId());
        }

        material.removeQuantity(quantity);
        if (material.getQuantity() == 0) {
            remove(material.getName());
        }

        notifyObservers(
                OperationType.TRANSFER,
                transferredMaterial,
                "Transferred " + transferredMaterial.getQuantity() + " of " + name + " from " + id + " to " + toWarehouse.getId()
        );
    }

    private int calculateAvailableCapacity(Material material) {
        Material existingMaterial = materials.get(material.getName());
        return existingMaterial == null ? material.getMaxCapacity() : material.getMaxCapacity() - existingMaterial.getQuantity();
    }

    private boolean validateMaterialAddition(Material material) {
        if (materials.containsKey(material.getName())) {
            return material.getQuantity() <= calculateAvailableCapacity(materials.get(material.getName()));
        }

        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Material> getMaterials() {
        return Map.copyOf(materials);
    }

    @Override
    public void printMaterials() {
        System.out.println("Warehouse ID: " + id);
        for (Material material : materials.values()) {
            System.out.println(material);
        }
    }

    @Override
    public void addObserver(WarehouseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WarehouseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(OperationType operation, Material material, String message) {
        for (WarehouseObserver observer : observers) {
            observer.update(operation, material, message);
        }
    }
}
