package fr.gendarmerienationale.reseauprevention31.struct;

public enum Secteur {

    ZONE_1,
    ZONE_2,
    ZONE_3,
    ZONE_4,
    ZONE_5,
    ZONE_6,
    ZONE_7;

    public int getNum() {
        return super.ordinal() + 1;
    }
}
