package database;

public enum Path {
    CARS("src/main/java/database/cars.dat"),
    DRIVERS("src/main/java//drivers.dat"),
    CLIENTS("src/main/java//clients.dat"),
    MANAGERS("src/main/java//managers.dat");

    private final String filePath;

    Path(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
