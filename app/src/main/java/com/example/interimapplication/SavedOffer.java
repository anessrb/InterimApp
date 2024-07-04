package com.example.interimapplication;


public class SavedOffer {
    private int photo;
    private String titre;
    private String nomCompagnie;
    private String salaire;
    private String lieu;
    private String duree;
    private String description;
    private String idUser;
    private String idOffer;
    private String id;

    public SavedOffer() {
        // Default constructor required for calls to DataSnapshot.getValue(SavedOffer.class)
    }

    public SavedOffer(int photo, String titre, String nomCompagnie, String salaire, String lieu, String duree, String description, String idUser, String idOffer, String id) {
        this.photo = photo;
        this.titre = titre;
        this.nomCompagnie = nomCompagnie;
        this.salaire = salaire;
        this.lieu = lieu;
        this.duree = duree;
        this.description = description;
        this.idUser = idUser;
        this.idOffer = idOffer;
        this.id = id;
    }

    // Getters and setters
    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
