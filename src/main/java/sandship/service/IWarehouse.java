package sandship.service;

import sandship.model.Material;

import java.util.Map;

public interface IWarehouse {
    void store(Material material) throws AddMaterialException;

    void remove(String name);

    void transfer(String name, int quantity, IWarehouse toWarehouse) throws TransferException;

    String getId();

    Map<String, Material> getMaterials();

    void printMaterials();
}
