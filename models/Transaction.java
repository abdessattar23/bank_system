package models;

import java.time.LocalDateTime;

public class Transaction {
    private int idTransaction;
    private String typeTransaction;
    private double montant;
    private LocalDateTime date;
    private String motif;
    private Compte compteSource;
    private Compte compteDestination;

    public Transaction(int idTransaction, String typeTransaction, double montant,
                      String motif, Compte compteSource) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas etre negatif");
        }
        this.idTransaction = idTransaction;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.date = LocalDateTime.now();
        this.motif = motif;
        this.compteSource = compteSource;
    }

    public Transaction(int idTransaction, String typeTransaction, double montant,
                      String motif, Compte compteSource, Compte compteDestination) {
        this(idTransaction, typeTransaction, montant, motif, compteSource);
        this.compteDestination = compteDestination;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMotif() {
        return motif;
    }

    public Compte getCompteSource() {
        return compteSource;
    }

    public Compte getCompteDestination() {
        return compteDestination;
    }

    public void setCompteDestination(Compte compteDestination) {
        this.compteDestination = compteDestination;
    }
}
