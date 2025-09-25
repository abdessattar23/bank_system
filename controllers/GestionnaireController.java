package controllers;

import models.*;
import services.CompteService;
import views.MenuView;
import java.util.*;

public class GestionnaireController {
    private CompteService compteService;
    private MenuView menuView;
    private Scanner scanner;

    public GestionnaireController(CompteService compteService, MenuView menuView) {
        this.compteService = compteService;
        this.menuView = menuView;
        this.scanner = new Scanner(System.in);
    }

    public void menuGestionnaire() {
        boolean continuer = true;
        while (continuer) {
            menuView.afficherMenuGestionnaire();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerClient();
                    break;
                case 2:
                    creerCompte();
                    break;
                case 3:
                    ajouterTransaction();
                    break;
                case 4:
                    consulterClients();
                    break;
                case 5:
                    consulterTransactions();
                    break;
                case 6:
                    detecterTransactionsSuspectes();
                    break;
                case 7:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    private void creerClient() {
        System.out.println("\n=== Creer un nouveau client ===");
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Prenom: ");
        String prenom = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        try {
            compteService.creerClient(id, nom, prenom, email, motDePasse);
            System.out.println("Client cree avec succes");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void creerCompte() {
        System.out.println("\n=== Creer un nouveau compte ===");
        System.out.print("ID du compte: ");
        int idCompte = scanner.nextInt();

        System.out.print("ID du client: ");
        int idClient = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Types de compte disponibles:");
        System.out.println("1. COURANT");
        System.out.println("2. EPARGNE");
        System.out.println("3. DEPOT_A_TERME");
        System.out.print("Votre choix: ");
        int choixType = scanner.nextInt();

        String typeCompte;
        switch (choixType) {
            case 1: typeCompte = "COURANT"; break;
            case 2: typeCompte = "EPARGNE"; break;
            case 3: typeCompte = "DEPOT_A_TERME"; break;
            default: typeCompte = "COURANT";
        }

        try {
            compteService.creerCompte(idCompte, typeCompte, idClient);
            System.out.println("Compte cree avec succes");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void ajouterTransaction() {
        System.out.println("\n=== Ajouter une transaction ===");
        System.out.println("1. Depot");
        System.out.println("2. Retrait");
        System.out.println("3. Virement");
        System.out.print("Type de transaction: ");

        int type = scanner.nextInt();
        scanner.nextLine();

        switch (type) {
            case 1:
                effectuerDepot();
                break;
            case 2:
                effectuerRetrait();
                break;
            case 3:
                effectuerVirement();
                break;
            default:
                System.out.println("Type invalide");
        }
    }

    private void effectuerDepot() {
        System.out.print("ID du compte: ");
        int idCompte = scanner.nextInt();

        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            compteService.effectuerDepot(idCompte, montant, motif);
            System.out.println("Depot effectue avec succes");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void effectuerRetrait() {
        System.out.print("ID du compte: ");
        int idCompte = scanner.nextInt();

        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            compteService.effectuerRetrait(idCompte, montant, motif);
            System.out.println("Retrait effectue avec succes");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void effectuerVirement() {
        System.out.print("ID du compte source: ");
        int idCompteSource = scanner.nextInt();

        System.out.print("ID du compte destination: ");
        int idCompteDestination = scanner.nextInt();

        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            compteService.effectuerVirement(idCompteSource, idCompteDestination, montant, motif);
            System.out.println("Virement effectue avec succes");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void consulterClients() {
        System.out.println("\n=== Liste des clients ===");
        for (Client client : compteService.getClients()) {
            System.out.println("ID: " + client.getIdClient() +
                             " - " + client.getNom() + " " + client.getPrenom());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Nombre de comptes: " + client.getComptes().size());
            System.out.println("Solde total: " + client.getSoldeTotal() + " DH");
            System.out.println("---");
        }
    }

    private void consulterTransactions() {
        System.out.println("\n=== Transactions ===");
        System.out.print("ID du client (0 pour toutes): ");
        int idClient = scanner.nextInt();

        if (idClient == 0) {
            for (Transaction transaction : compteService.getTransactions()) {
                afficherTransaction(transaction);
            }
        } else {
            Client client = compteService.trouverClientParId(idClient);
            if (client != null) {
                for (Compte compte : client.getComptes()) {
                    for (Transaction transaction : compte.getTransactions()) {
                        afficherTransaction(transaction);
                    }
                }
            }
        }
    }

    private void afficherTransaction(Transaction transaction) {
        System.out.println("ID: " + transaction.getIdTransaction());
        System.out.println("Type: " + transaction.getTypeTransaction());
        System.out.println("Montant: " + transaction.getMontant() + " DH");
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Motif: " + transaction.getMotif());
        System.out.println("Compte source: " + transaction.getCompteSource().getIdCompte());
        if (transaction.getCompteDestination() != null) {
            System.out.println("Compte destination: " + transaction.getCompteDestination().getIdCompte());
        }
        System.out.println("---");
    }

    private void detecterTransactionsSuspectes() {
        System.out.println("\n=== Transactions suspectes ===");
        double seuilMontant = 10000.0;

        List<Transaction> transactionsSuspectes = compteService.getTransactions().stream()
            .filter(t -> t.getMontant() > seuilMontant)
            .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));

        if (transactionsSuspectes.isEmpty()) {
            System.out.println("Aucune transaction suspecte detectee");
        } else {
            for (Transaction transaction : transactionsSuspectes) {
                System.out.println("ALERTE - Montant eleve: " + transaction.getMontant() + " DH");
                afficherTransaction(transaction);
            }
        }
    }
}
