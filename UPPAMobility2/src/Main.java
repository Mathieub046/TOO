import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import mobility.*;

public class Main {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        System.out.println("OK - " + message);
    }

    private static void refused(Runnable operation, String message) {
        try {
            operation.run();
        } catch (IllegalArgumentException | IllegalStateException expected) {
            System.out.println("OK - " + message);
            return;
        }
        throw new AssertionError(message);
    }

    public static void main(String[] args) {
        MobilityService service = new MobilityService();
        User alice = new User("U-1", "Alice");
        LocalDateTime start = LocalDateTime.of(2026, 10, 12, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 12, 11, 30);
        Station campus = new Station("Campus Nord", 5);
        Station library = new Station("Bibliothèque", 5);
        Bike b1 = new Bike("B-104");
        Bike b2 = new ElectricBike("EB-201", 50);
        Bike b3 = new ElectricBike("EB-202", 5);
        List<Bike> bikes = Arrays.asList(b1, b2, b3);
        for (Bike bike : bikes) {
            System.out.println(bike.getId() + " can be rented: " + bike.canBeRented());
        }
        check(b1.canBeRented() && b2.canBeRented() && !b3.canBeRented(), "Polymorphism");

        campus.addBike(b1);
        Rental classic = service.rentBike(alice, b1, campus, start);
        check(!b1.isAvailable() && !campus.containsBike(b1), "Classic bike rented");
        refused(() -> service.rentBike(alice, b1, campus, start), "Double rental refused");
        service.returnBike(classic, library, end);
        check(!classic.isActive() && b1.isAvailable() && library.containsBike(b1), "Classic bike returned");

        campus.addBike(b2);
        Rental electric = service.rentBike(alice, b2, campus, start);
        check(!b2.isAvailable(), "Electric bike at 50% rented");
        refused(() -> service.rentBike(alice, b2, campus, start), "Second electric rental refused");
        service.returnBike(electric, library, end);
        check(b2.isAvailable() && library.containsBike(b2), "Electric bike returned");

        Bike threshold = new ElectricBike("EB-203", 10);
        campus.addBike(threshold);
        Rental atTen = service.rentBike(alice, threshold, campus, start);
        check(atTen.isActive(), "Electric bike at 10% rented");
        service.returnBike(atTen, campus, end);

        campus.addBike(b3);
        refused(() -> service.rentBike(alice, b3, campus, start), "Electric bike at 5% refused");
        check(campus.containsBike(b3) && b3.isAvailable(), "Rejected rental leaves state unchanged");
        refused(() -> new ElectricBike("EB-X", 101), "Battery above 100 refused");
        refused(() -> new ElectricBike("EB-X", -1), "Negative battery refused");

        Station full = new Station("Full", 1);
        full.addBike(new Bike("B-FULL"));
        refused(() -> full.addBike(new Bike("B-OTHER")), "Station capacity enforced");
        Rental pending = service.rentBike(alice, threshold, campus, start);
        refused(() -> service.returnBike(pending, full, end), "Return to full station refused");
        check(pending.isActive() && !threshold.isAvailable(), "Failed return preserves rental");
        refused(() -> service.returnBike(pending, campus, start.minusMinutes(1)), "Invalid return time refused");
        check(pending.isActive(), "Invalid time preserves rental");
        service.returnBike(pending, campus, end);
        System.out.println("All TP1 and TP2 checks passed.");
    }
}