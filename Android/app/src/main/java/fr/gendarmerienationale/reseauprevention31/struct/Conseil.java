package fr.gendarmerienationale.reseauprevention31.struct;

public class Conseil {

    private int    id;
    private String objet;
    private String texte;

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
