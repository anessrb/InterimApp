package com.example.interimapplication;

public class ManageOffers {
    private int photo;
    private String titre;
    private String nomCompagnie;
    private String lieu;
    private String salaire;
    private String temps;
    private String duree;

    private String description;
    private String id;

    public ManageOffers(){}

    public ManageOffers(int photo, String titre, String nomCompagnie, String lieu, String salaire, String temps, String duree, String description, String id) {
        this.photo = photo;
        this.titre = titre;
        this.nomCompagnie = nomCompagnie;
        this.lieu = lieu;
        this.salaire = salaire;
        this.temps = temps;
        this.duree = duree;
        this.description = description;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
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
}
