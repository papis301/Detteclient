package com.example.dettesclients;

public class Dette {
    private int id;
    private int clientId;
    private double montant;
    private String description;
    private String date;
    private boolean paye; // nouveau champ

    // Constructeurs
    public Dette(int id, int clientId, double montant, String description, String date, boolean paye) {
        this.setId(id);
        this.setClientId(clientId);
        this.setMontant(montant);
        this.setDescription(description);
        this.setDate(date);
        this.paye = paye;
    }

    public Dette(int clientId, double montant, String description, String date) {
        this(clientId, montant, description, date, false);
    }

    public Dette(int clientId, double montant, String description, String date, boolean paye) {
        this.setClientId(clientId);
        this.setMontant(montant);
        this.setDescription(description);
        this.setDate(date);
        this.paye = paye;
    }

    // Getters et Setters
    public boolean isPaye() { return paye; }
    public void setPaye(boolean paye) { this.paye = paye; }
    public int getId() { return id; }
    public double getMontant() { return montant; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public int getClientId() { return clientId; }


    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
