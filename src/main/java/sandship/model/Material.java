package sandship.model;

public class Material {
    private final String name;
    private final String description;
    private final String icon;
    private final int maxCapacity;
    private int quantity;

    public Material(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.icon = builder.icon;
        this.quantity = builder.quantity;
        this.maxCapacity = builder.maxCapacity;
    }

    public static class Builder {
        private final String name;
        private final String description;
        private final String icon;
        private final int maxCapacity;
        private int quantity = 0;

        public Builder(String name, String description, String icon, int maxCapacity) {
            this.name = name;
            this.description = description;
            this.icon = icon;
            this.maxCapacity = maxCapacity;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Material build() {
            return new Material(this);
        }
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
        return String.format(
                "Material{name='%s', description='%s', icon='%s', maxCapacity=%d, quantity=%d}",
                name, description, icon, maxCapacity, quantity
        );
    }
}
