package fr.gendarmerienationale.reseauprevention31.struct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Commune {

    private int     id;
    private int     code_postal;
    private String  nom;
    private Secteur secteur;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode_postal() {
        return this.code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
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
        return nom + " (" + code_postal + ") " + " - " + secteur.toString();
    }
}
