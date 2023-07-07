package database.Enum;

public enum EAccountStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCKED("blocked");
    private final String accountStatus;

    EAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }
}