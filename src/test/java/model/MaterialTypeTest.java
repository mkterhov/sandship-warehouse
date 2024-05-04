package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sandship.model.MaterialType;

public class MaterialTypeTest {
    private MaterialType material;

    @BeforeEach
    public void setUp() {
        material = new MaterialType("Wood", "Used for building stuff", "wood_icon.png", 100);
    }

    @Test
    public void testSetMaxCapacity() {
        assertEquals(100, material.getMaxCapacity(), "Max Quantity should be 100");
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
