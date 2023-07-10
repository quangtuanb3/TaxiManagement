package DAO.Enum;

public enum EPath {
    CARS("src\\main\\java\\DAO\\cars.txt"),
    DRIVERS("src\\main\\java\\DAO\\drivers.txt"),
    CLIENTS("src\\main\\java\\DAO\\clients.txt"),
    RIDES("src\\main\\java\\DAO\\rides.txt"),
    MANAGERS("src\\main\\java\\DAO\\managers.txt");

    private final String filePath;

    EPath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
