package Data.Enum;

public enum EPath {
    CARS("D:\\CODEGYM\\M2\\TaxiManagement\\src\\main\\java\\Data\\Output\\cars.txt"),
    DRIVERS("D:\\CODEGYM\\M2\\TaxiManagement\\src\\main\\java\\Data\\Output\\drivers.txt"),
    CLIENTS("D:\\CODEGYM\\M2\\TaxiManagement\\src\\main\\java\\Data\\Output\\clients.txt"),
    RIDES("D:\\CODEGYM\\M2\\TaxiManagement\\src\\main\\java\\Data\\Output\\rides.txt"),
    MANAGERS("D:\\CODEGYM\\M2\\TaxiManagement\\src\\main\\java\\Data\\Output\\managers.txt");

    private final String filePath;

    EPath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
