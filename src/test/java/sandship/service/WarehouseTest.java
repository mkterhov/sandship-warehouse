package sandship.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sandship.model.Material;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class WarehouseTest {
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Material material1;
    private Material material2;

    @BeforeEach
    public void setUp() throws Exception {
        warehouse1 = new Warehouse("W1");
        warehouse2 = new Warehouse("W2");
        material1 = new Material("Steel", "A strong metal", "steel_icon.png", 100);
        material2 = new Material("Wood", "Used for building stuff", "wood_icon.png", 100);
        material1.setQuantity(50);
        material2.setQuantity(90);

        warehouse1.store(material1);
        warehouse1.store(material2);
    }

    @Test
    public void testStore() {
        Material material3 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        assertDoesNotThrow(() -> {
            warehouse1.store(material3);
        });
        assertEquals(3, warehouse1.getMaterials().size());
        assertEquals(material3, warehouse1.getMaterials().get("Copper"));
    }

    @Test
    public void testStoreThrowsException() {
        Material materialOverCapacity = new Material("Steel", "A strong metal", "steel_icon.png", 200);
        materialOverCapacity.setQuantity(150);

        assertThrows(AddMaterialException.class, () -> {
            warehouse1.store(materialOverCapacity);
        });
    }

    @Test
    public void testRemoveByName() {
        warehouse1.removeByName("Steel");
        assertEquals(1, warehouse1.getMaterials().size());
        assertFalse(warehouse1.getMaterials().containsKey("Steel"));

        warehouse1.removeByName("Wood");
        assertEquals(0, warehouse1.getMaterials().size());
    }

    @Test
    public void testTransfer() throws TransferException, AddMaterialException {
        Material material3 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        material3.setQuantity(60);
        warehouse1.store(material3);

        warehouse1.transfer("Copper", 60, warehouse2);
        assertFalse(warehouse1.getMaterials().containsKey("Copper"));
        assertEquals(60, warehouse2.getMaterials().get("Copper").getQuantity());
        Material material4 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        material4.setQuantity(40);
        warehouse1.store(material4);

        warehouse1.transfer("Copper", 40, warehouse2);
        assertNull(warehouse1.getMaterials().get("Copper"));
        assertEquals(100, warehouse2.getMaterials().get("Copper").getQuantity());
    }

    @Test
    public void testTransferMaterialThrowsExceptionWhenRequestingTransferOfNonExisting() {
        assertThrows(TransferException.class, () -> {
            warehouse1.transfer("Copper", 200, warehouse2);
        });
    }

    @Test
    public void testTransferMaterialThrowsTransferException() throws AddMaterialException {
        Material material3 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        material3.setQuantity(100);
        warehouse1.store(material3);

        Material materialInWarehouse2 = new Material("Copper", "A conductive metal", "copper_icon.png", 100);
        materialInWarehouse2.setQuantity(90);
        warehouse2.store(materialInWarehouse2);
        warehouse1.printMaterials();
        warehouse2.printMaterials();

        TransferException exception = assertThrows(TransferException.class, () -> {
            warehouse1.transfer("Copper", 20, warehouse2);
        });

        assertEquals(
                "Failed to transfer 20 of Copper from W1 to W2",
                exception.getMessage()
        );
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