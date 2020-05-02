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

    public static Secteur getSecteur(int numSecteur) {
        for (Secteur secteur : Secteur.values()) {
            if (secteur.getNum() == numSecteur)
                return secteur;
        }

        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ").replace("z", "Z");
    }
}
