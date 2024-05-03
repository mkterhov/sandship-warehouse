package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sandship.model.Material;

public class MaterialTest {
    private Material material;

    @BeforeEach
    public void setUp() {
        material = new Material("Wood", "Used for building stuff", "wood_icon.png", 10, 100);
    }

    @Test
    public void testSetQuantityWithinBounds() {
        try {
            material.setQuantity(50);
        } catch (Exception e) {
            fail("Setting quantity within bounds should not throw an exception");
        }
        assertEquals(50, material.getQuantity(), "Quantity should be updated to 50");
    }

    @Test
    public void testSetQuantityExceedsMaxAmount() {
        Exception exception = assertThrows(Exception.class, () -> {
            material.setQuantity(150);
        });

        assertEquals("quantity bigger than max", exception.getMessage(), "Exception message should match expected text");
    }

    @Test
    public void testGetQuantityInitialValue() {
        assertEquals(10, material.getQuantity(), "Initial quantity should be 10");
    }

    @Test
    public void testGetName() {
        assertEquals("Wood", material.getName(), "Name should be 'Wood'");
    }

    @Test
    public void testGetDescription() {
        assertEquals("Used for building stuff", material.getDescription(), "Description should match the provided text");
    }

    @Test
    public void testGetIcon() {
        assertEquals("wood_icon.png", material.getIcon(), "Icon should match the provided filename");
    }
}
