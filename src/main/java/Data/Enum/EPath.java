package Data.Enum;

import utils.AppUtils;

public enum EPath {
    CARS("src\\main\\java\\Data\\Output\\cars.txt"),
    DRIVERS("src\\main\\java\\Data\\Output\\drivers.txt"),
    CLIENTS("src\\main\\java\\Data\\Output\\clients.txt"),
    RIDES("src\\main\\java\\Data\\Output\\rides.txt"),
    MANAGERS("src\\main\\java\\Data\\Output\\managers.txt");

    private final String filePath;

    EPath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return AppUtils.getDirectoryPath(filePath);
    }
}
