package com.example.interimapplication;

public class Application {
    private int photo;
    private String titre;
    private String nomCompagnie;
    private String statut;
    private String date;
    private String userId;
    private String companyId;
    private String offerId;
    private String id;
    private String seen;

    public Application(){}

    public Application(int photo, String titre, String nomCompagnie, String statut, String date, String userId, String companyId, String offerId, String id) {
        this.photo = photo;
        this.titre = titre;
        this.nomCompagnie = nomCompagnie;
        this.statut = statut;
        this.date = date;
        this.userId = userId;
        this.companyId = companyId;
        this.id = id;
        this.offerId=offerId;
        this.seen = "false";
    }

    public String isSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
