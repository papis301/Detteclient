package com.example.dettesclients;

public class Dette {
    private int id;
    private int clientId;
    private double montant;
    private String description;
    private String date;

    public Dette(int id, int clientId, double montant, String description, String date) {
        this.id = id;
        this.clientId = clientId;
        this.montant = montant;
        this.description = description;
        this.date = date;
    }

    public Dette(int clientId, double montant, String description, String date) {
        this.clientId = clientId;
        this.montant = montant;
        this.description = description;
        this.date = date;
    }

    public int getClientId() { return clientId; }
    public double getMontant() { return montant; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
}
