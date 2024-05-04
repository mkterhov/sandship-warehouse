package sandship.service;


import sandship.model.MaterialType;

interface IWarehouse {
    void transfer(IWarehouse to, MaterialType material, int amount);
    void add(MaterialType material, int amount);
    void remove(MaterialType material, int amount);
}
