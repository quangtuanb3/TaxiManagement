package Data.Enum;

public enum ECarStatus {
    WAITING_ASSIGN("Not assign"),
    USING("using"),
    REPAIRING("unavailable"),
    USED("used");
    private final String status;

    ECarStatus(String status) {
        this.status = status;
    }

    public String getCarStatus() {
        return status;
    }
}
