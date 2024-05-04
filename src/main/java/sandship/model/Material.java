package sandship.model;

public class Material {
    private final MaterialType type;
    private int quantity;

    public Material(MaterialType type, int quantity) throws IllegalArgumentException{
        this.type = type;
        if (quantity < 0 || quantity > type.getMaxCapacity()) {
            throw new IllegalArgumentException("Quantity must be between 0 and the max capacity.");
        }
        this.quantity = quantity;
    }

    public MaterialType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }


    public void addQuantity(int quantityToAdd) throws IllegalArgumentException{
        if (quantityToAdd < 0) {
            throw new IllegalArgumentException("Quantity to add cannot be negative.");
        }

        if (this.quantity + quantityToAdd > type.getMaxCapacity()) {
            throw new IllegalArgumentException("Exceeds max capacity for this material.");
        }

        this.quantity += quantityToAdd;
    }

    public void removeQuantity(int quantityToRemove) throws IllegalArgumentException{
        if (quantityToRemove < 0) {
            throw new IllegalArgumentException("Quantity to remove cannot be negative.");
        }

        if (this.quantity - quantityToRemove < 0) {
            throw new IllegalArgumentException("Cannot remove more than the current quantity.");
        }

        this.quantity -= quantityToRemove;
    }
}
