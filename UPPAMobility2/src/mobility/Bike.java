package mobility;
public class Bike {
    private final String id;
    private boolean available = true;
    public Bike(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Invalid bike id");
        this.id = id;
    }
    public String getId() { return id; }
    public boolean isAvailable() { return available; }
    // Accessible uniquement aux classes du package mobility.                   
    void setAvailable(boolean available) { this.available = available; }
}

