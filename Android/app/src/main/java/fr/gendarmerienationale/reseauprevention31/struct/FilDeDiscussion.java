package fr.gendarmerienationale.reseauprevention31.struct;

public class FilDeDiscussion {

    private int         id;
    private Utilisateur utilisateur;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
