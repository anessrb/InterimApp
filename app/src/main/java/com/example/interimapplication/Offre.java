package com.example.interimapplication;

public class Offre {
    private int photo;
    private String titre;
    private String nomCompagnie;
    private String lieu;
    private String salaire;
    private String temps;
    private String duree;


    public Offre(int p, String t, String n, String l, String s, String te, String d){
        photo = p;
        titre = t;
        nomCompagnie = n;
        lieu = l;
        salaire = s;
        temps = te;
        duree = d;
    }
    public int getPhoto(){
        return photo;
    }
    public String getTitre(){
        return titre;
    }
    public String getNomCompagnie(){
        return nomCompagnie;
    }
    public String getLieu(){
        return lieu;
    }
    public void setPhoto(int p){
        photo = p;
    }
    public void setTitre(String t){
        titre = t;
    }
    public void setNomCompagnie(String n){
        nomCompagnie = n;
    }
    public void setLieu(String l){
        lieu = l;
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
}
