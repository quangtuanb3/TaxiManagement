package Data.Enum;

public enum ECarStatus {
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
