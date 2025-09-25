package views;

public class MenuView {

    public void afficherMenuPrincipal() {
        System.out.println("\n======= BANQUE MAROCAINE =======");
        System.out.println("1. Se connecter comme client");
        System.out.println("2. Se connecter comme gestionnaire");
        System.out.println("3. Quitter");
        System.out.print("Votre choix: ");
    }

    public void afficherMenuClient() {
        System.out.println("\n=== MENU CLIENT ===");
        System.out.println("1. Consulter mes informations");
        System.out.println("2. Consulter mes comptes");
        System.out.println("3. Voir historique des transactions");
        System.out.println("4. Filtrer mes transactions");
        System.out.println("5. Calculer mes totaux");
        System.out.println("6. Se deconnecter");
        System.out.print("Votre choix: ");
    }

    public void afficherMenuGestionnaire() {
        System.out.println("\n=== MENU GESTIONNAIRE ===");
        System.out.println("1. Creer un client");
        System.out.println("2. Creer un compte");
        System.out.println("3. Ajouter une transaction");
        System.out.println("4. Consulter les clients");
        System.out.println("5. Consulter les transactions");
        System.out.println("6. Detecter transactions suspectes");
        System.out.println("7. Se deconnecter");
        System.out.print("Votre choix: ");
    }

    public void afficherBienvenue() {
        System.out.println("Bienvenue dans le systeme bancaire!");
    }

    public void afficherAuRevoir() {
        System.out.println("Au revoir et merci d'avoir utilise notre systeme!");
    }

    public void afficherErreur(String message) {
        System.out.println("ERREUR: " + message);
    }

    public void afficherSucces(String message) {
        System.out.println("SUCCES: " + message);
    }
}
