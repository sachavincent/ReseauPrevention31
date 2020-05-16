package fr.gendarmerienationale.reseauprevention31.struct;

import androidx.annotation.NonNull;
import java.util.Date;

public class Annonce {

    private int     id;
    private Date    date;
    private String  texte;
    private boolean vu;

    public boolean isVue() {
        return this.vu;
    }

    public void setVue(boolean vu) {
        this.vu = vu;
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

    @NonNull
    @Override
    public String toString() {
        return "Annonce (id=" + id + ", date=" + date.toString() + ", texte=" + texte;
    }
}
