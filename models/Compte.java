package models;

import java.util.ArrayList;
import java.util.List;

public class Compte {
    private int idCompte;
    private String typeCompte;
    private double solde;
    private List<Transaction> transactions;
    private Client client;

    public Compte(int idCompte, String typeCompte, Client client) {
        this.idCompte = idCompte;
        this.typeCompte = typeCompte;
        this.solde = 0.0;
        this.transactions = new ArrayList<>();
        this.client = client;
    }

    public void ajouterTransaction(Transaction transaction) {
        transactions.add(transaction);
        String type = transaction.getTypeTransaction();
        double montant = transaction.getMontant();
        switch (type) {
            case "DEPOT":
                solde += montant;
                break;
            case "RETRAIT":
                if (solde < montant) {
                    throw new ArithmeticException("Solde insuffisant pour ce retrait");
                }
                solde -= montant;
                break;
            case "VIREMENT":
                if (solde < montant) {
                    throw new ArithmeticException("Solde insuffisant pour ce virement");
                }
                solde -= montant;
                if (transaction.getCompteDestination() != null) {
                    transaction.getCompteDestination().recevoirVirement(montant);
                }
                break;
            default:
                break;
        }
    }

    public void recevoirVirement(double montant) {
        solde += montant;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public double getSolde() {
        return solde;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Client getClient() {
        return client;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
