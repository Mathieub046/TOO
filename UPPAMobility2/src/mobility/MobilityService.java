package mobility;
import java.time.LocalDateTime;
public class MobilityService {
    public Rental rentBike(User user, Bike bike, Station station, LocalDateTime startTime) {
        if (user == null || bike == null || station == null || startTime == null) throw new IllegalArgumentException("Missing argument");
        if (!bike.isAvailable()) throw new IllegalStateException("Bike is not available");
        if (!station.containsBike(bike)) throw new IllegalArgumentException("Bike does not belong to this station");
        Rental rental = new Rental(user, bike, startTime);
        station.removeBike(bike);
        bike.setAvailable(false);
        return rental;
    }
    public void returnBike(Rental rental, Station station, LocalDateTime returnTime) {
        if (rental == null || station == null || returnTime == null) throw new IllegalArgumentException("Missing argument");
        if (!rental.isActive()) throw new IllegalStateException("Rental already finished");
        if (station.isFull()) throw new IllegalStateException("Station is full");
        if (returnTime.isBefore(rental.getStartTime())) throw new IllegalArgumentException("Invalid return time");
        Bike bike = rental.getBike();
        if (station.containsBike(bike)) throw new IllegalStateException("Bike already at station");
        // Preconditions verifiees avant toute modification.                                                                                                                            
        bike.setAvailable(true);
        station.addBike(bike);
        rental.finish(returnTime);
    }
}

