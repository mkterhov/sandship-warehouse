package sandship.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sandship.model.Material;

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
        material1 = new Material.Builder("Steel", "A strong metal", "steel_icon.png", 100)
                .quantity(50)
                .build();
        material2 = new Material.Builder("Wood", "Used for building stuff", "wood_icon.png", 100)
                .quantity(90)
                .build();

        warehouse1.store(material1);
        warehouse1.store(material2);
    }

    @Test
    public void testStore() {
        Material material3 = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 100)
                .build();

        assertDoesNotThrow(() -> {
            warehouse1.store(material3);
        });

        assertEquals(3, warehouse1.getMaterials().size());
        assertEquals(material3, warehouse1.getMaterials().get("Copper"));
    }

    @Test
    public void testStoreThrowsException() {
        Material materialOverCapacity = new Material.Builder("Steel", "A strong metal", "steel_icon.png", 200)
                .quantity(150)
                .build();

        assertThrows(AddMaterialException.class, () -> {
            warehouse1.store(materialOverCapacity);
        });
    }

    @Test
    public void testTransfer() throws TransferException, AddMaterialException {
        Material material3 = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 100)
                .quantity(60)
                .build();

        warehouse1.store(material3);

        warehouse1.transfer("Copper", 60, warehouse2);
        assertFalse(warehouse1.getMaterials().containsKey("Copper"));
        assertEquals(60, warehouse2.getMaterials().get("Copper").getQuantity());

        Material material4 = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 100)
                .quantity(40)
                .build();

        warehouse1.store(material4);

        warehouse1.transfer("Copper", 40, warehouse2);
        assertNull(warehouse1.getMaterials().get("Copper"));
        assertEquals(100, warehouse2.getMaterials().get("Copper").getQuantity());
    }

    @Test
    public void testTransferMaterialThrowsExceptionWhenRequestingTransferOfNonExisting() {
        TransferException exception = assertThrows(TransferException.class, () -> {
            warehouse1.transfer("Copper", 200, warehouse2);
        });

        assertEquals(
                "The requested material not found in W1!",
                exception.getMessage()
        );
    }

    @Test
    public void testTransferMaterialThrowsTransferException() throws AddMaterialException {
        Material material3 = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 100)
                .quantity(100)
                .build();
        warehouse1.store(material3);

        Material materialInWarehouse2 = new Material.Builder("Copper", "A conductive metal", "copper_icon.png", 100)
                .quantity(90)
                .build();
        warehouse2.store(materialInWarehouse2);

        TransferException exception = assertThrows(TransferException.class, () -> {
            warehouse1.transfer("Copper", 20, warehouse2);
        });

        assertEquals(
                "Failed to transfer 20 of Copper from W1 to W2",
                exception.getMessage()
        );
    }
}
