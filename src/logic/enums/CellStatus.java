package logic.enums;

public enum CellStatus {

    HIT("$"),
    MISS("#"),
    SHIP("@"),
    INITIAL("*");

    private String sign;

    CellStatus(String sign) {
        this.sign = sign;
    }

    public String sign() {
        return sign;
    }
}
