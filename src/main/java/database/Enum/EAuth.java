package database.Enum;

public enum EAuth {
    ADMIN("admin"),
    DRIVER("driver"),
    CLIENT("client");

    private final String auth;

    EAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }
}
