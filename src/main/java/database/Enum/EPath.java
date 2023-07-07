package database.Enum;

public enum EPath {
    CARS("cars.txt"),
    DRIVERS("drivers.txt"),
    CLIENTS("src/main/java/database/clients"),
    RIDES("rides.txt"),
    MANAGERS("managers.txt");

    private final String filePath;

    EPath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
