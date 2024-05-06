package sandship.model;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final String id;
    private Map<String, Material> materials = new HashMap<>();

    public Warehouse(String id) {
        this.id = id;
    }

    public void addMaterial(Material material) {
        materials.put(material.getName(), material);
    }

    public void removeMaterial(String name) {
        materials.remove(name);
    }

    public void transferMaterial(String name, Warehouse otherWarehouse) {
        Material material = materials.get(name);
        if (material != null) {
            otherWarehouse.addMaterial(material);
            this.removeMaterial(name);
        }
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
}
