package fr.gendarmerienationale.reseauprevention31.struct;

import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CA;
import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CCI;
import static fr.gendarmerienationale.reseauprevention31.struct.Chambre.CMA;

public class Utilisateur {

    private int          id;
    private String       cle;
    private CodeActivite code_act;
    private Secteur      secteur;
    private int          code_postal;
    private String       telephone;
    private String       mail;
    private Chambre      chambre;

    public int getId() {
        return this.id;
    }

    public String getCle() {
        return this.cle;
    }

    public CodeActivite getCode_act() {
        return this.code_act;
    }

    public Secteur getSecteur() {
        return this.secteur;
    }

    public int getCode_postal() {
        return this.code_postal;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getMail() {
        return this.mail;
    }

    public Chambre getChambre() {
        return this.chambre;
    }

    public void setCode_act(CodeActivite code_act) {
        this.code_act = code_act;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
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

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
