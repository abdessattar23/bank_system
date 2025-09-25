package services;

import models.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class CompteService {
    private List<Client> clients;
    private List<Compte> comptes;
    private List<Transaction> transactions;
    private int nextTransactionId;

    public CompteService() {
        this.clients = new ArrayList<>();
        this.comptes = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.nextTransactionId = 1;
    }

    public void creerClient(int id, String nom, String prenom, String email, String motDePasse) {
        Client client = new Client(id, nom, prenom, email, motDePasse);
        clients.add(client);
    }

    public void supprimerClient(int idClient) {
        Client client = trouverClientParId(idClient);
        if (client != null) {
            clients.remove(client);
        }
    }

    public Compte creerCompte(int idCompte, String typeCompte, int idClient) {
        Client client = trouverClientParId(idClient);
        if (client == null) {
            throw new NoSuchElementException("Client introuvable");
        }

        Compte compte = new Compte(idCompte, typeCompte, client);
        comptes.add(compte);
        client.ajouterCompte(compte);
        return compte;
    }

    public void effectuerDepot(int idCompte, double montant, String motif) {
        Compte compte = trouverCompteParId(idCompte);
        if (compte == null) {
            throw new NoSuchElementException("Compte introuvable");
        }

        Transaction transaction = new Transaction(nextTransactionId++, "DEPOT",
                                                montant, motif, compte);
        compte.ajouterTransaction(transaction);
        transactions.add(transaction);
    }

    public void effectuerRetrait(int idCompte, double montant, String motif) {
        Compte compte = trouverCompteParId(idCompte);
        if (compte == null) {
            throw new NoSuchElementException("Compte introuvable");
        }

        if (compte.getSolde() < montant) {
            throw new ArithmeticException("Solde insuffisant");
        }

        Transaction transaction = new Transaction(nextTransactionId++, "RETRAIT",
                                                montant, motif, compte);
        compte.ajouterTransaction(transaction);
        transactions.add(transaction);
    }

    public void effectuerVirement(int idCompteSource, int idCompteDestination,
                                 double montant, String motif) {
        Compte compteSource = trouverCompteParId(idCompteSource);
        Compte compteDestination = trouverCompteParId(idCompteDestination);

        if (compteSource == null || compteDestination == null) {
            throw new NoSuchElementException("Compte introuvable");
        }

        if (compteSource.getSolde() < montant) {
            throw new ArithmeticException("Solde insuffisant pour le virement");
        }

        Transaction transaction = new Transaction(nextTransactionId++, "VIREMENT",
                                                montant, motif, compteSource, compteDestination);
        compteSource.ajouterTransaction(transaction);
        transactions.add(transaction);
    }

    public Client trouverClientParId(int id) {
        return clients.stream()
                .filter(c -> c.getIdClient() == id)
                .findFirst()
                .orElse(null);
    }

    public Compte trouverCompteParId(int id) {
        return comptes.stream()
                .filter(c -> c.getIdCompte() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Transaction> getTransactionsParCompte(int idCompte) {
        return transactions.stream()
                .filter(t -> t.getCompteSource().getIdCompte() == idCompte)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsParType(String type) {
        return transactions.stream()
                .filter(t -> t.getTypeTransaction().equals(type))
                .collect(Collectors.toList());
    }

    public double calculerTotalDepots(int idClient) {
        return transactions.stream()
                .filter(t -> t.getTypeTransaction().equals("DEPOT") &&
                           t.getCompteSource().getClient().getIdClient() == idClient)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    public double calculerTotalRetraits(int idClient) {
        return transactions.stream()
                .filter(t -> t.getTypeTransaction().equals("RETRAIT") &&
                           t.getCompteSource().getClient().getIdClient() == idClient)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
