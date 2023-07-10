package DAO.Enum;

public enum EAccountStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCKED("blocked");
    private final String accountStatus;

    EAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
//hahahaha
    public String getAccountStatus() {
        return accountStatus;
    }
}