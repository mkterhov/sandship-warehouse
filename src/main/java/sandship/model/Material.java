package sandship.model;

public class Material {
    private final String name;
    private final String description;
    private final String icon;
    private final int maxCapacity;
    private int quantity;

    public Material(String name, String description, String icon, int maxCapacity) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.quantity = 0;
        this.maxCapacity = maxCapacity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean addQuantity(int amount) {
        if (this.quantity + amount <= maxCapacity) {
            this.quantity += amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeQuantity(int amount) {
        if (this.quantity - amount >= 0) {
            this.quantity -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", quantity=" + quantity +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
