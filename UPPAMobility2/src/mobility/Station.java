package mobility;
import java.util.ArrayList;
import java.util.List;
public class Station {
    private final String name;
    private final int capacity;
    private final List<Bike> bikes = new ArrayList<>();
    public Station(String name, int capacity) {
        if (name == null || name.isBlank() || capacity < 0) throw new IllegalArgumentException("Invalid station");
        this.name = name;
        this.capacity = capacity;
    }
    public String getName() { return name; }
    public boolean isFull() { return bikes.size() >= capacity; }
    public void addBike(Bike bike) {
        if (bike == null) throw new IllegalArgumentException("Bike is null");
        if (isFull()) throw new IllegalStateException("Station is full");
        if (bikes.contains(bike)) throw new IllegalStateException("Bike already at station");
        if (!bike.isAvailable()) throw new IllegalStateException("Bike is not available");
        bikes.add(bike);
    }
    public void removeBike(Bike bike) {
        if (!bikes.remove(bike)) throw new IllegalArgumentException("Bike does not belong to this station");
    }
    public boolean containsBike(Bike bike) { return bikes.contains(bike); }
}



