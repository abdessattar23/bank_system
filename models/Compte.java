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

        if (transaction.getTypeTransaction().equals("DEPOT")) {
            solde += transaction.getMontant();
        } else if (transaction.getTypeTransaction().equals("RETRAIT")) {
            if (solde >= transaction.getMontant()) {
                solde -= transaction.getMontant();
            } else {
                throw new ArithmeticException("Solde insuffisant pour ce retrait");
            }
        } else if (transaction.getTypeTransaction().equals("VIREMENT")) {
            if (solde >= transaction.getMontant()) {
                solde -= transaction.getMontant();
                if (transaction.getCompteDestination() != null) {
                    transaction.getCompteDestination().recevoirVirement(transaction.getMontant());
                }
            } else {
                throw new ArithmeticException("Solde insuffisant pour ce virement");
            }
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
