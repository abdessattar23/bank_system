package models;

import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {
    private int idClient;
    private List<Compte> comptes;

    public Client(int idClient, String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idClient = idClient;
        this.comptes = new ArrayList<>();
    }

    public void ajouterCompte(Compte compte) {
        comptes.add(compte);
    }

    public void supprimerCompte(Compte compte) {
        comptes.remove(compte);
    }

    public double getSoldeTotal() {
        double total = 0.0;
        for (Compte compte : comptes) {
            total += compte.getSolde();
        }
        return total;
    }

    public int getIdClient() {
        return idClient;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
