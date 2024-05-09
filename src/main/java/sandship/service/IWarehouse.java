package sandship.service;

import sandship.model.Material;

import java.util.Map;

public interface IWarehouse {
    void store(Material material) throws AddMaterialException;

    void removeByName(String name);

    void transfer(String name, int quantity, IWarehouse toWarehouse) throws TransferException;

    int getAvailableCapacity(Material material);

    String getId();

    Map<String, Material> getMaterials();

    void setMaterials(Map<String, Material> materials);

    void printMaterials();
}
