package controllers;

import models.*;
import services.CompteService;
import views.MenuView;
import java.util.*;

public class ClientController {
    private CompteService compteService;
    private MenuView menuView;
    private Scanner scanner;

    public ClientController(CompteService compteService, MenuView menuView) {
        this.compteService = compteService;
        this.menuView = menuView;
        this.scanner = new Scanner(System.in);
    }

    public void menuClient(int idClient) {
        Client client = compteService.trouverClientParId(idClient);
        if (client == null) {
            System.out.println("Client introuvable");
            return;
        }

        boolean continuer = true;
        while (continuer) {
            menuView.afficherMenuClient();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    consulterInfos(client);
                    break;
                case 2:
                    consulterComptes(client);
                    break;
                case 3:
                    consulterHistorique(client);
                    break;
                case 4:
                    filtrerTransactions(client);
                    break;
                case 5:
                    calculerTotaux(client);
                    break;
                case 6:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    private void consulterInfos(Client client) {
        System.out.println("\n=== Informations personnelles ===");
        System.out.println("ID: " + client.getIdClient());
        System.out.println("Nom: " + client.getNom());
        System.out.println("Prenom: " + client.getPrenom());
        System.out.println("Email: " + client.getEmail());
    }

    private void consulterComptes(Client client) {
        System.out.println("\n=== Mes comptes ===");
        for (Compte compte : client.getComptes()) {
            System.out.println("Compte ID: " + compte.getIdCompte());
            System.out.println("Type: " + compte.getTypeCompte());
            System.out.println("Solde: " + compte.getSolde() + " DH");
            System.out.println("---");
        }
        System.out.println("Solde total: " + client.getSoldeTotal() + " DH");
    }

    private void consulterHistorique(Client client) {
        System.out.println("\n=== Historique des transactions ===");
        for (Compte compte : client.getComptes()) {
            System.out.println("Compte " + compte.getIdCompte() + ":");
            for (Transaction transaction : compte.getTransactions()) {
                System.out.println("  - " + transaction.getTypeTransaction() +
                                 " de " + transaction.getMontant() + " DH");
                System.out.println("    Date: " + transaction.getDate());
                System.out.println("    Motif: " + transaction.getMotif());
            }
        }
    }

    private void filtrerTransactions(Client client) {
        System.out.println("\n=== Filtrer les transactions ===");
        System.out.println("1. Par type (DEPOT/RETRAIT/VIREMENT)");
        System.out.println("2. Par montant minimum");
        System.out.print("Votre choix: ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix == 1) {
            System.out.print("Entrez le type (DEPOT/RETRAIT/VIREMENT): ");
            String type = scanner.nextLine().toUpperCase();

            for (Compte compte : client.getComptes()) {
                compte.getTransactions().stream()
                    .filter(t -> t.getTypeTransaction().equals(type))
                    .forEach(t -> {
                        System.out.println(t.getTypeTransaction() + " - " +
                                         t.getMontant() + " DH - " + t.getDate());
                    });
            }
        } else if (choix == 2) {
            System.out.print("Montant minimum: ");
            double montantMin = scanner.nextDouble();

            for (Compte compte : client.getComptes()) {
                compte.getTransactions().stream()
                    .filter(t -> t.getMontant() >= montantMin)
                    .forEach(t -> {
                        System.out.println(t.getTypeTransaction() + " - " +
                                         t.getMontant() + " DH - " + t.getDate());
                    });
            }
        }
    }

    private void calculerTotaux(Client client) {
        System.out.println("\n=== Totaux ===");
        double totalDepots = compteService.calculerTotalDepots(client.getIdClient());
        double totalRetraits = compteService.calculerTotalRetraits(client.getIdClient());

        System.out.println("Total des depots: " + totalDepots + " DH");
        System.out.println("Total des retraits: " + totalRetraits + " DH");
        System.out.println("Solde total: " + client.getSoldeTotal() + " DH");
    }
}
