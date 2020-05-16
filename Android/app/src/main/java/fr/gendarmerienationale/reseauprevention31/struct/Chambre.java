package fr.gendarmerienationale.reseauprevention31.struct;

public enum Chambre {
    CCI,
    CMA,
    CA;

    public static Chambre getChambre(String nomChambre) {
        for (Chambre chambre : Chambre.values()) {
            if (chambre.toString().equalsIgnoreCase(nomChambre))
                return chambre;
        }

        return null;
    }
}
