import java.time.LocalDateTime;
import mobility.*;
public class Main {
    public static void main(String[] args) {
        MobilityService service = new MobilityService();
        Station north = new Station("Campus Nord", 2);
        Station library = new Station("Bibliotheque", 2);
        User alice = new User("U-1", "Alice");
        Bike bike = new Bike("B-104");
        north.addBike(bike);
        System.out.println("B-104 initially at Campus Nord: " + north.containsBike(bike));
        LocalDateTime start = LocalDateTime.of(2026, 10, 12, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 12, 11, 30);
        Rental rental = service.rentBike(alice, bike, north, start);
        System.out.println("Alice rents B-104 at 09:00");
        System.out.println("B-104 available: " + bike.isAvailable());
        System.out.println("B-104 at Campus Nord: " + north.containsBike(bike));
        service.returnBike(rental, library, end);
        System.out.println("Alice returns B-104 at Bibliotheque at 11:30");
        System.out.println("Rental active: " + rental.isActive());
        System.out.println("B-104 now at Bibliotheque: " + library.containsBike(bike));
        System.out.println("B-104 available: " + bike.isAvailable());
        System.out.println("\n--- Tests des invariants ---");
        Station full = new Station("Station pleine", 1);
        full.addBike(new Bike("B-1"));
        test("Station pleine", () -> full.addBike(new Bike("B-2")));
        Station source = new Station("Depart", 2);
        Bike rented = new Bike("B-3");
        source.addBike(rented);
        Rental active = service.rentBike(alice, rented, source, start);
        test("Velo deja emprunte", () -> service.rentBike(alice, rented, source, start));
        test("Heure de retour invalide", () -> service.returnBike(active, library, start.minusMinutes(1)));
        System.out.println("Location toujours active apres heure invalide: " + active.isActive());
        test("Retour dans une station pleine", () -> service.returnBike(active, full, end));
        System.out.println("Location toujours active apres retour refuse: " + active.isActive());
        System.out.println("Velo absent de la station pleine: " + !full.containsBike(rented));
    }
    private static void test(String label, Runnable operation) {
        try { operation.run(); System.out.println(label + ": ECHEC (operation acceptee)"); }
        catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(label + ": OK (" + e.getMessage() + ")");
        }
    }
}

