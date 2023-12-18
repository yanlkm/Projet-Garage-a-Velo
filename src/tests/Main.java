package tests;

import affichages.VisiteurDetails;
import elements.Velo;
import facade.GarageVelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import reponses.Roue;
import reponses.VerifierResultat;


/**
 * Classe principale contenant le point d'entrée pour créer les informations de
 * plusieurs vélos.
 */
public class Main {
  /**
  * Méthode principale permettant de créer et d'afficher les informations de
  * plusieurs vélos.
  *
  * @param args Les arguments de la ligne de commande.
  */
  public static void main(String[] args) {
    GarageVelo garage = new GarageVelo();

    garage.configurerUsine("MarqueA", "MarqueBatterie1", "MarquePneu1", 100, 28, true);
    garage.creerVelo("MarqueA", "Modele1");
    garage.creerVelo("MarqueB", "Modele2");
    garage.configurerUsine("MarqueB", "MarqueBatterie2", "MarquePneu2", 120, 23, false);
    garage.creerVelo("MarqueC", "Modele3");

    System.out.println(); 
    garage.appliquerVisiteurGlobal();

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
    String heureFormattee = now.format(formatter);
    String numeroSerieStr = heureFormattee + String.format("%04d", 0);
    Integer numeroCree = Integer.parseInt(numeroSerieStr);
    Velo veloA = GarageVelo.recupListe().get(numeroCree);
    VisiteurDetails visiteurDetails = new VisiteurDetails();
    System.out.println(); 

    veloA.applique(visiteurDetails);

    garage.selectionVelo(veloA);
    System.out.println(); 

    // Validation après la sélection d'un vélo
    System.out.println("Taille des observers après sélection : " + garage.sizeObservers());
    System.out.println(); 

    // Changer une roue (avant puis arrière)
    VerifierResultat resultatChangementRoue1 = garage.changerRoue(numeroCree, Roue.AV,
        "NewBrand", 29, true);
    System.out.println("Résultat du changement de roue : " + resultatChangementRoue1);
    System.out.println();
    VerifierResultat resultatChangementRoue2 = garage.changerRoue(numeroCree, Roue.ARR,
            "NewBrand", 29, true);
    System.out.println("Résultat du changement de roue : " + resultatChangementRoue2);
    System.out.println(); 

    // Changer la batterie
    VerifierResultat resultatChangementBatterie = garage.changerBatterie(numeroCree, 150,
        "NewBatteryBrand");
    System.out.println("Résultat du changement de batterie : " + resultatChangementBatterie);
    System.out.println(); 

    // Supprimer un vélo
    VerifierResultat resultatSuppression = garage.supprimerVelo(numeroCree);
    System.out.println("Résultat de la suppression : " + resultatSuppression);
    System.out.println(); 

    // Créer des vélos à partir d'un fichier JSON
    VerifierResultat resultatCreationFichier = garage
        .creerVeloapartirfichier("fichiers_json/velos_valides.json");
    System.out.println("Résultat de la création depuis un fichier : " + resultatCreationFichier);
    System.out.println(); 

    // Affichage après les modifications
    System.out.println("Affichage à partir de garageVelo après les modifications");
    garage.appliquerVisiteurGlobal();

    System.out.println(); 
    
    //affichage de chaque vélos 
    System.out.println("Affichage de chaque vélo en détail");
    for (Velo velo : GarageVelo.recupListe().values()) {
      garage.appliquerVisiteurDetails(velo.getNumeroSerie()); 
      System.out.println(); 
    }
  }
}
