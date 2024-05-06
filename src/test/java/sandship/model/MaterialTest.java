package sandship.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialTest {
    private Material material;

    @BeforeEach
    public void setUp() {
        material = new Material("Wood", "Used for building stuff", "wood_icon.png", 100);
    }

    @Test
    public void testGetName() {
        assertEquals("Wood", material.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Used for building stuff", material.getDescription());
    }

    @Test
    public void testGetIcon() {
        assertEquals("wood_icon.png", material.getIcon());
    }

    @Test
    public void testGetMaxCapacity() {
        assertEquals(100, material.getMaxCapacity());
    }

    @Test
    public void testSetQuantityWithinBounds() {
        material.setQuantity(50);
        assertEquals(50, material.getQuantity());
    }

    @Test
    public void testSetQuantityAboveBounds() {
        material.setQuantity(150);
        assertEquals(150, material.getQuantity());
    }

    @Test
    public void testAddQuantityWithinBounds() {
        assertTrue(material.addQuantity(50));
        assertEquals(50, material.getQuantity());

        assertTrue(material.addQuantity(30));
        assertEquals(80, material.getQuantity());
    }

    @Test
    public void testAddQuantityAboveBounds() {
        assertTrue(material.addQuantity(70));
        assertFalse(material.addQuantity(50));
        assertEquals(70, material.getQuantity());
    }

    @Test
    public void testRemoveQuantityWithinBounds() {
        material.setQuantity(50);
        assertTrue(material.removeQuantity(30));
        assertEquals(20, material.getQuantity());

        assertTrue(material.removeQuantity(20));
        assertEquals(0, material.getQuantity());
    }

    @Test
    public void testRemoveQuantityBelowBounds() {
        material.setQuantity(50);
        assertFalse(material.removeQuantity(60));
        assertEquals(50, material.getQuantity());
    }
}