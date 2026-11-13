package mobility;

public class ElectricBike extends Bike {
    private final int batteryLevel;

    public ElectricBike(String id, int batteryLevel) {
        super(id);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Invalid battery level");
        }
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() { return batteryLevel; }

    @Override
    public boolean canBeRented() {
        return super.canBeRented() && batteryLevel >= 10;
    }
}