package sandship.service;


import sandship.model.Material;

interface IWarehouse {
    void transfer(IWarehouse to, Material material, int amount);
    void add(Material material, int amount);
    void removed(Material material, int amount);
}
