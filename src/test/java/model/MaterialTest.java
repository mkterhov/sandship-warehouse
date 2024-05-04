package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sandship.model.Material;
import sandship.model.MaterialType;

class MaterialTest {

    private MaterialType iron;
    private Material material;

    @BeforeEach
    void setUp() {
        iron = new MaterialType("Iron", "A strong metal", "iron_icon.png", 100);
        material = new Material(iron, 50);
    }

    @Test
    void testConstructorValid() {
        assertEquals(iron, material.getType());
        assertEquals(50, material.getQuantity());
    }

    @Test
    void testConstructorInvalidNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Material(iron, -1));
    }

    @Test
    void testConstructorInvalidOverCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Material(iron, 101));
    }

    @Test
    void testAddQuantityValid() {
        material.addQuantity(20);
        assertEquals(70, material.getQuantity());
    }

    @Test
    void testAddQuantityInvalidNegative() {
        assertThrows(IllegalArgumentException.class, () -> material.addQuantity(-10));
    }

    @Test
    void testAddQuantityInvalidOverCapacity() {
        assertThrows(IllegalArgumentException.class, () -> material.addQuantity(51));
    }

    @Test
    void testRemoveQuantityValid() {
        material.removeQuantity(20);
        assertEquals(30, material.getQuantity());
    }

    @Test
    void testRemoveQuantityInvalidNegative() {
        assertThrows(IllegalArgumentException.class, () -> material.removeQuantity(-10));
    }

    @Test
    void testRemoveQuantityInvalidOverdraw() {
        assertThrows(IllegalArgumentException.class, () -> material.removeQuantity(60));
    }
}
