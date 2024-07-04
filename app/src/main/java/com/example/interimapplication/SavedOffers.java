package com.example.interimapplication;

public class SavedOffers {
    private String idUser;
    private String idOffer;
    private String id;

    public SavedOffers(String idUser, String idOffer, String id) {
        this.idUser = idUser;
        this.idOffer = idOffer;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SavedOffers() {}

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
}