package fr.gendarmerienationale.reseauprevention31.struct;

import androidx.annotation.NonNull;
import java.util.Date;

public class Message {

    private int    id;
    private Date   date;
    private String texte;

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
        return "Message (id=" + id + ", date=" + date.toString() + ", texte=" + texte;
    }
}