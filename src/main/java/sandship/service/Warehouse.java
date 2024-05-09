package sandship.service;

import sandship.model.Material;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements IWarehouse {
    private final String id;
    private Map<String, Material> materials = new HashMap<>();

    public Warehouse(String id) {
        this.id = id;
    }

    @Override
    public void store(Material material) throws AddMaterialException {
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

    @Override
    public void removeByName(String name) {
        materials.remove(name);
    }

    @Override
    public void transfer(String name, int quantity, IWarehouse toWarehouse) throws TransferException {
        if (!materials.containsKey(name)) {
            throw new TransferException("The requested material not found in " + id  + "!");
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
            toWarehouse.store(transferredMaterial);
        } catch (AddMaterialException e){
            throw new TransferException("Failed to transfer " + quantity + " of " + name + " from " + id + " to " + toWarehouse.getId());
        }

        material.removeQuantity(quantity);
        if (material.getQuantity() == 0) {
            removeByName(material.getName());
        }
    }

    @Override
    public int getAvailableCapacity(Material material) {
        if (materials.containsKey(material.getName())) {
            Material existingMaterial = materials.get(material.getName());
            return material.getMaxCapacity() - existingMaterial.getQuantity();
        }

        return material.getMaxCapacity();
    }

    private boolean validateMaterialAddition(Material material) {
        if (materials.containsKey(material.getName())) {
            return material.getQuantity() <= getAvailableCapacity(materials.get(material.getName()));
        }

        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Material> getMaterials() {
        return materials;
    }

    @Override
    public void setMaterials(Map<String, Material> materials) {
        this.materials = materials;
    }

    @Override
    public void printMaterials() {
        System.out.println("Warehouse ID: " + id);
        for (Material material : materials.values()) {
            System.out.println("Material: " + material.getName() + ", Quantity: " + material.getQuantity() + ", Max Capacity: " + material.getMaxCapacity());
        }
    }
}
