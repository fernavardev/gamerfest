package com.example.gamerfest;

public class EnlacesCompartir {

    private String webUrl;
    private String twitter;
    private String facebook;
    private String whatsapp;

    public EnlacesCompartir() {
    }

    public EnlacesCompartir(
            String webUrl,
            String twitter,
            String facebook,
            String whatsapp) {
        this.webUrl = webUrl;
        this.twitter = twitter;
        this.facebook = facebook;
        this.whatsapp = whatsapp;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
