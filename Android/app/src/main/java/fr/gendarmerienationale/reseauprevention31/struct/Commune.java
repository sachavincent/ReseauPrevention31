package fr.gendarmerienationale.reseauprevention31.struct;

import androidx.annotation.NonNull;

public class Commune {

    private int     id;
    private int     codePostal;
    private String  nom;
    private Secteur secteur;

    public Commune() {
    }

    public Commune(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodePostal() {
        return this.codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Secteur getSecteur() {
        return this.secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    @NonNull
    @Override
    public String toString() {
        return nom + " (" + codePostal + ") " + " - " + secteur;
    }
}
