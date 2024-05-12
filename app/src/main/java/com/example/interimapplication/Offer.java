package com.example.interimapplication;

public class Offer {
    private int photo;
    private String titre;
    private String nomCompagnie;
    private String candidateName;
    private String number;
    private String sex;
    private String lastDegree;
    private String adress;
    private String age;

    public Offer(int photo, String titre, String nomCompagnie, String candidateName, String number, String sex, String lastDegree, String adress, String age) {
        this.photo = photo;
        this.titre = titre;
        this.nomCompagnie = nomCompagnie;
        this.candidateName = candidateName;
        this.number = number;
        this.sex = sex;
        this.lastDegree = lastDegree;
        this.adress = adress;
        this.age = age;
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

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
