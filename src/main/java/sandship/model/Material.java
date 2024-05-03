package sandship.model;

public class Material {
    private final String name;
    private final String description;
    private final String icon;
    private int quantity;
    private final int maxAmount;

    public Material(String name, String description, String icon, int quantity, int maxAmount) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.quantity = quantity;
        this.maxAmount = maxAmount;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity > maxAmount)
        {
            throw new Exception("quantity bigger than max");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
