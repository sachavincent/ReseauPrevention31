package fr.gendarmerienationale.reseauprevention31.struct;

import java.util.Date;

public class Message {

    private int             id;
    private Date            date;
    private String          texte;
    private FilDeDiscussion fil;
    private boolean         vu;

    public boolean isVu() {
        return this.vu;
    }

    public void setVu(boolean vu) {
        this.vu = vu;
    }

    public FilDeDiscussion getFil() {
        return this.fil;
    }

    public void setFil(FilDeDiscussion fil) {
        this.fil = fil;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
