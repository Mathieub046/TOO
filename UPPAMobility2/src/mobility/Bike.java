package mobility;

public class Bike {
    private final String id;
    private boolean available;

    public Bike(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invalid bike id");
        }
        this.id = id;
        this.available = true;
    }

    public String getId() { return id; }
    public boolean isAvailable() { return available; }
    public boolean canBeRented() { return isAvailable(); }

    void markRented() {
        if (!canBeRented()) throw new IllegalStateException("Bike cannot be rented");
        available = false;
    }

    void markReturned() {
        if (available) throw new IllegalStateException("Bike is already available");
        available = true;
    }
}