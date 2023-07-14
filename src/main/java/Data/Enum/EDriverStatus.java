package Data.Enum;

public enum EDriverStatus {
    WAITING_ASSIGN("waiting assign"),
    AVAILABLE("available"),
    UNAVAILABLE("unavailable"),
    ON_TRIP("on_trip");
    private final String status;

    EDriverStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
