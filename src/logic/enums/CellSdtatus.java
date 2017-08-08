package logic.enums;

public enum CellSdtatus {

    HIT("$"),
    MISS("#"),
    SHIP("@"),
    INITIAL("*");

    private String sign;

    CellSdtatus(String sign) {
        this.sign = sign;
    }

    public String sign() {
        return sign;
    }
}
