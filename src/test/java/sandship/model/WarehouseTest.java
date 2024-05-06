package sandship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class WarehouseTest {
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Material material1;
    private Material material2;

    @BeforeEach
    public void setUp() {
        warehouse1 = new Warehouse("W1");
        warehouse2 = new Warehouse("W2");
        material1 = new Material("Steel", "A strong metal", "steel_icon.png", 100);
        material2 = new Material("Wood", "Used for building stuff", "wood_icon.png", 100);
        warehouse1.addMaterial(material1);
        warehouse1.addMaterial(material2);
    }

    @Test
    public void testAddMaterial() {
        Material material3 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        warehouse1.addMaterial(material3);
        assertEquals(3, warehouse1.getMaterials().size());
        assertEquals(material3, warehouse1.getMaterials().get("Copper"));
    }

    @Test
    public void testRemoveMaterial() {
        warehouse1.removeMaterial("Steel");
        assertEquals(1, warehouse1.getMaterials().size());
        assertFalse(warehouse1.getMaterials().containsKey("Steel"));

        warehouse1.removeMaterial("Wood");
        assertEquals(0, warehouse1.getMaterials().size());
    }

    @Test
    public void testTransferMaterial() {
        warehouse1.transferMaterial("Steel", warehouse2);
        assertFalse(warehouse1.getMaterials().containsKey("Steel"));
        assertTrue(warehouse2.getMaterials().containsKey("Steel"));

        warehouse2.transferMaterial("Steel", warehouse1);
        assertFalse(warehouse2.getMaterials().containsKey("Steel"));
        assertTrue(warehouse1.getMaterials().containsKey("Steel"));
    }

    @Test
    public void testGetId() {
        assertEquals("W1", warehouse1.getId());
    }

    @Test
    public void testGetMaterials() {
        Map<String, Material> expectedMaterials = new HashMap<>();
        expectedMaterials.put("Steel", material1);
        expectedMaterials.put("Wood", material2);
        assertEquals(expectedMaterials, warehouse1.getMaterials());
    }

    @Test
    public void testSetMaterials() {
        Map<String, Material> newMaterials = new HashMap<>();
        newMaterials.put("Copper", new Material("Copper", "A conductive metal", "copper_icon.png", 100));
        warehouse1.setMaterials(newMaterials);
        assertEquals(1, warehouse1.getMaterials().size());
        assertTrue(warehouse1.getMaterials().containsKey("Copper"));
    }
}