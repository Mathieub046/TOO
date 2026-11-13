package mobility;

import java.time.LocalDateTime;

public class MobilityService {
    public Rental rentBike(User user, Bike bike, Station station, LocalDateTime startTime) {
        if (user == null || bike == null || station == null || startTime == null) {
            throw new IllegalArgumentException("Invalid rental request");
        }
        if (!station.containsBike(bike)) {
            throw new IllegalArgumentException("Bike does not belong to this station");
        }
        if (!bike.canBeRented()) throw new IllegalStateException("Bike cannot be rented");

        Rental rental = new Rental(user, bike, startTime);
        station.removeBike(bike);
        bike.markRented();
        return rental;
    }

    public void returnBike(Rental rental, Station station, LocalDateTime returnTime) {
        if (rental == null || station == null || returnTime == null) {
            throw new IllegalArgumentException("Invalid return request");
        }
        if (!rental.isActive()) throw new IllegalStateException("Rental already finished");
        if (station.isFull()) throw new IllegalStateException("Station is full");
        if (returnTime.isBefore(rental.getStartTime())) {
            throw new IllegalArgumentException("Invalid return time");
        }
        if (station.containsBike(rental.getBike())) {
            throw new IllegalStateException("Bike already in station");
        }

        rental.finish(returnTime);
        rental.getBike().markReturned();
        station.addBike(rental.getBike());
    }
}