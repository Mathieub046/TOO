package mobility;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private final String name;
    private final int capacity;
    private final List<Bike> bikes = new ArrayList<>();

    public Station(String name, int capacity) {
        if (name == null || name.isBlank() || capacity < 0) {
            throw new IllegalArgumentException("Invalid station");
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() { return name; }
    public boolean isFull() { return bikes.size() >= capacity; }
    public boolean containsBike(Bike bike) { return bikes.contains(bike); }

    public void addBike(Bike bike) {
        if (bike == null || !bike.isAvailable()) {
            throw new IllegalArgumentException("Invalid bike for station");
        }
        if (isFull()) throw new IllegalStateException("Station is full");
        if (containsBike(bike)) throw new IllegalStateException("Bike already in station");
        bikes.add(bike);
    }

    public void removeBike(Bike bike) {
        if (!bikes.remove(bike)) {
            throw new IllegalArgumentException("Bike does not belong to this station");
        }
    }
}