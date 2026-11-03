package mobility;
import java.time.LocalDateTime;
public class Rental {
    private final User user;
    private final Bike bike;
    private final LocalDateTime startTime;
    private LocalDateTime returnTime;
    public Rental(User user, Bike bike, LocalDateTime startTime) {
        if (user == null || bike == null || startTime == null) throw new IllegalArgumentException("Invalid rental");
        this.user = user;
        this.bike = bike;
        this.startTime = startTime;
    }
    public User getUser() { return user; }
    public Bike getBike() { return bike; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getReturnTime() { return returnTime; }
    public boolean isActive() { return returnTime == null; }
    public void finish(LocalDateTime returnTime) {
        if (!isActive()) throw new IllegalStateException("Rental already finished");
        if (returnTime == null || returnTime.isBefore(startTime)) throw new IllegalArgumentException("Invalid return time");
        this.returnTime = returnTime;
    }
}

