package database.Enum;

public enum ECarType {
    FOUR(4, 9000, 10500, 9000, 20000),
    SEVEN(7, 10000, 11000, 10000, 22000);

    private final int seat;
    private final int openPrice;
    private final int priceUnder30;
    private final int priceUpper30;
    private final int waitPrice;

    ECarType(int seat, int openPrice, int priceUnder30, int priceUpper30, int waitPrice) {
        this.seat = seat;
        this.openPrice = openPrice;
        this.priceUnder30 = priceUnder30;
        this.priceUpper30 = priceUpper30;
        this.waitPrice = waitPrice;
    }

    public int getSeat() {
        return seat;
    }


    public int getWaitPrice() {
        return waitPrice;
    }

    public int getOpenPrice() {
        return openPrice;
    }

    public int getPriceUnder30() {
        return priceUnder30;
    }

    public int getPriceUpper30() {
        return priceUpper30;
    }
}

