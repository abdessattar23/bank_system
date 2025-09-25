import controllers.*;
import services.CompteService;
import views.MenuView;
import java.util.Scanner;

// Application bancaire simple
public class Main {
    private static CompteService compteService;
    private static ClientController clientController;
    private static GestionnaireController gestionnaireController;
    private static MenuView menuView;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialiser les services et controllers
        compteService = new CompteService();
        menuView = new MenuView();
        clientController = new ClientController(compteService, menuView);
        gestionnaireController = new GestionnaireController(compteService, menuView);
        scanner = new Scanner(System.in);

        // Ajouter quelques donnees de test
        initDonneesTest();

        // Demarrer l'application
        demarrer();
    }

    private static void initDonneesTest() {
        // Creer quelques clients de test
        compteService.creerClient(1, "Alami", "Hassan", "hassan@email.com", "123456");
        compteService.creerClient(2, "Benali", "Fatima", "fatima@email.com", "654321");

        // Creer quelques comptes bancaires
        compteService.creerCompte(101, "COURANT", 1);
        compteService.creerCompte(102, "EPARGNE", 1);
        compteService.creerCompte(201, "COURANT", 2);

        // Ajouter quelques transactions
        compteService.effectuerDepot(101, 5500, "Salaire");
        compteService.effectuerDepot(102, 2500, "Economies");
        compteService.effectuerRetrait(101, 300, "Retrait DAB");
        compteService.effectuerVirement(101, 201, 1000, "Virement vers ami");
    }

    private static void demarrer() {
        menuView.afficherBienvenue();

        boolean continuer = true;
        while (continuer) {
            menuView.afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    connexionClient();
                    break;
                case 2:
                    connexionGestionnaire();
                    break;
                case 3:
                    continuer = false;
                    menuView.afficherAuRevoir();
                    break;
                default:
                    System.out.println("Choix invalide, veuillez reessayer");
            }
        }

        scanner.close();
    }

    private static void connexionClient() {
        System.out.print("Entrez votre ID client: ");
        int idClient = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        // Verification simple du mot de passe
        if (compteService.trouverClientParId(idClient) != null) {
            System.out.println("Connexion reussie!");
            clientController.menuClient(idClient);
        } else {
            System.out.println("Client introuvable ou mot de passe incorrect");
        }
    }

    private static void connexionGestionnaire() {
        System.out.print("Nom d'utilisateur gestionnaire: ");
        String nom = scanner.nextLine();

        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        // Verification simple (en production, utiliser une vraie authentification)
        if (nom.equals("admin") && motDePasse.equals("admin123")) {
            System.out.println("Connexion gestionnaire reussie!");
            gestionnaireController.menuGestionnaire();
        } else {
            System.out.println("Identifiants incorrects");
        }
    }
}
