package fr.gendarmerienationale.reseauprevention31.struct;

public class FilDeDiscussion {

    private int         id;
    private Utilisateur utilisateur;
    private Message     dernierMessage;
    private String      objet;

    public Message getDernierMessage() {
        return this.dernierMessage;
    }

    public void setDernierMessage(Message dernierMessage) {
        this.dernierMessage = dernierMessage;
    }

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

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "FilDeDiscussion{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", objet='" + objet + '\'' +
                ", dernierMessage='" + (dernierMessage == null ? null : dernierMessage.getId()) + '\'' +
                '}';
    }
}
