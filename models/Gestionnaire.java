package models;

import java.util.ArrayList;
import java.util.List;

public class Gestionnaire extends Personne {
    private int idGestionnaire;
    private String departement;
    private List<Client> listeClients;

    public Gestionnaire(int idGestionnaire, String nom, String prenom, String email,
                       String motDePasse, String departement) {
        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = idGestionnaire;
        this.departement = departement;
        this.listeClients = new ArrayList<>();
    }

    public void ajouterClient(Client client) {
        listeClients.add(client);
    }

    public void supprimerClient(Client client) {
        listeClients.remove(client);
    }

    public int getIdGestionnaire() {
        return idGestionnaire;
    }

    public String getDepartement() {
        return departement;
    }

    public List<Client> getListeClients() {
        return listeClients;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
}
