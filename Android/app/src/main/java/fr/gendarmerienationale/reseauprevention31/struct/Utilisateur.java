package fr.gendarmerienationale.reseauprevention31.struct;

import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CA;
import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CCI;
import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CMA;

public class Utilisateur {

    private int    id;
    private String cle;

    private String       prenom;
    private String       nom;
    private String       numeroSiret;
    private CodeActivite codeActivite;
    private Secteur      secteur;
    private String       numeroTelephone;
    private String       nomSociete;
    private String       mail;
    private Commune      commune;
    private Chambre      chambre;


    public Commune getCommune() {
        return this.commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getNomSociete() {
        return this.nomSociete;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroSiret() {
        return this.numeroSiret;
    }

    public void setNumeroSiret(String numeroSiret) {
        this.numeroSiret = numeroSiret;
    }

    public int getId() {
        return this.id;
    }

    public String getCle() {
        return this.cle;
    }

    public CodeActivite getCodeActivite() {
        return this.codeActivite;
    }

    public Secteur getSecteur() {
        return this.secteur;
    }

    public String getNumeroTelephone() {
        return this.numeroTelephone;
    }

    public String getMail() {
        return this.mail;
    }

    public Chambre getChambre() {
        return this.chambre;
    }

    public void setCodeActivite(CodeActivite codeActivite) {
        this.codeActivite = codeActivite;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public void setChambre(String chambre) {
        switch (chambre) {
            case "CCI":
                this.chambre = CCI;
                break;
            case "CMA":
                this.chambre = CMA;
                break;
            case "CA":
                this.chambre = CA;
                break;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", cle='" + cle + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", numeroSiret='" + numeroSiret + '\'' +
                ", codeActivite=" + codeActivite +
                ", secteur=" + secteur +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", nomSociete='" + nomSociete + '\'' +
                ", mail='" + mail + '\'' +
                ", commune=" + commune +
                ", chambre=" + chambre +
                '}';
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }
}
