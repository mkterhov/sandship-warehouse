package sandship.service;

import sandship.model.Material;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final String id;
    private Map<String, Material> materials = new HashMap<>();

    public Warehouse(String id) {
        this.id = id;
    }

    public void addMaterial(Material material) throws AddMaterialException {
        if (!validateMaterialAddition(material)) {
            throw new AddMaterialException("Failed to add material " + material.getName() + ". Exceeds max capacity.");
        }

        if (materials.containsKey(material.getName())) {
            Material existingMaterial = materials.get(material.getName());
            existingMaterial.addQuantity(material.getQuantity());
            material.removeQuantity(material.getQuantity());

            return;
        }

        materials.put(material.getName(), material);
    }

    public void removeMaterial(String name) {
        materials.remove(name);
    }

    public void transferMaterial(String name, int quantity, Warehouse toWarehouse) throws TransferException {
        if (!materials.containsKey(name)) {
            throw new TransferException("Failed to transfer " + quantity + " of " + name + " from " + id + " to " + toWarehouse.getId());
        }

        Material material = materials.get(name);
        Material transferredMaterial = new Material(
                material.getName(),
                material.getDescription(),
                material.getIcon(),
                material.getMaxCapacity()
        );

        transferredMaterial.setQuantity(quantity);
        try
        {
            toWarehouse.addMaterial(transferredMaterial);

        } catch (AddMaterialException e){
            throw new TransferException("Failed to transfer " + quantity + " of " + name + " from " + id + " to " + toWarehouse.getId());
        }

        material.removeQuantity(quantity);
        if (material.getQuantity() == 0) {
            removeMaterial(material.getName());
        }
    }

    public int getAvailableCapacity(Material material) {
        int currentQuantity = materials.getOrDefault(
                material.getName(),
                new Material(material.getName(),
                        "",
                        "",
                        material.getMaxCapacity())
        ).getQuantity();
        return material.getMaxCapacity() - currentQuantity;
    }

    private boolean validateMaterialAddition(Material material) {
        if (materials.containsKey(material.getName())) {
            return material.getQuantity() <= getAvailableCapacity(materials.get(material.getName()));
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public Map<String, Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Map<String, Material> materials) {
        this.materials = materials;
    }

    public void outputMaterials() {
        System.out.println("Warehouse ID: " + id);
        for (Material material : materials.values()) {
            System.out.println("Material: " + material.getName() + ", Quantity: " + material.getQuantity() + ", Max Capacity: " + material.getMaxCapacity());
        }
    }
}
