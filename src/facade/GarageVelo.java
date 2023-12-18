package facade;



import affichages.VisiteurDetails;
import affichages.VisiteurGlobal;
import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import interfaces.Element;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.VisiteurElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import reponses.Roue;
import reponses.VerifierResultat;
import usines.UsineFichier;
import usines.UsineVelo;
import verifications.VerifierVelo;

/**
 * Classe qui vérifie la conformité d'un Vélo.
 */
public class GarageVelo implements Element, Observable {
  /**
  * Attributs .
  */ 
  private static HashMap<Integer, Velo> listeVelos; 
  private static UsineVelo usineCreation;
  private List<Observer> observers;
  private static Velo veloSelectionne; 
  
  /**
   * Constructeur GarageVelo.
   */
  public GarageVelo() {
    listeVelos = new HashMap<>(); 
    usineCreation =  new UsineVelo();
    observers = new ArrayList<>();
    veloSelectionne = null; 
  }
  
  /**
   * Selectionneur d'un velo.
   *
   * @param velo velo selectionné
   */
  public void selectionVelo(Velo velo) {
    GarageVelo.veloSelectionne = velo; 
    notifyObservers();
  }
  
  // Création de vélos 
  public HashMap<Integer, Velo> getMap() {
    return listeVelos;
  }
  
  
  /**
   * Crée un vélo et le stocke dans un garage.
   *
   * @param marqueVelo marque du vélo qu'on souahite créer
   * @param model modèle du vélo qu'on souhaite créer
   * @return retourne vrai si le vélo est créé ou faux sinon. 
   */
  public VerifierResultat creerVelo(String marqueVelo, String model) {
    if (marqueVelo == null) {
      return VerifierResultat.MARQUE_INCONNU; 
    }
    if (model == null) {
      return VerifierResultat.MODEL_INCONNU; 
    }

    usineCreation.modifierMarqueVelo(marqueVelo); 
 
    Velo veloCreation = usineCreation.creerVelo(model); 
    

    listeVelos.put((Integer) veloCreation.getNumeroSerie(), veloCreation);
    
    return VerifierVelo.estConforme(veloCreation); 
  }
  
  /**
   * Applique le visiteur global à la liste de vélos dans le garage.
   * Cela affiche les informations de tous les vélos dans la console.
   */
  public void appliquerVisiteurGlobal() {
    VisiteurGlobal visiteurGlobal = new VisiteurGlobal();
    visiteurGlobal.agitSur(listeVelos);
  }

  /**
   * Applique le visiteur de détails à un vélo spécifique dans la liste de vélos.
   * Cela affiche les détails du vélo spécifié dans la console.
   *
   * @param numeroSerie Le numéro de série du vélo à inspecter.
   */
  public void appliquerVisiteurDetails(Integer numeroSerie) {
    VisiteurDetails visiteurDetails = new VisiteurDetails();
    Velo velo = listeVelos.get(numeroSerie);
    visiteurDetails.agitSur(velo);
  }
  
  /**
   * Configure l'usine avec les spécifications fournies pour la création ultérieure de vélos.
   *
   * @param marqueVelo       La marque du vélo à configurer.
   * @param marqueBatterie   La marque de la batterie à configurer.
   * @param marquePneu       La marque du pneu à configurer.
   * @param puissance        La puissance de la batterie à configurer.
   * @param largeurPneu      La largeur du pneu à configurer.
   * @param contientChambre  La présence d'une chambre à air dans le pneu à configurer.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat configurerUsine(String marqueVelo, String marqueBatterie, 
             String marquePneu, Integer puissance, Integer largeurPneu, boolean contientChambre) {
    usineCreation.configurerPneu(marquePneu, largeurPneu, contientChambre);
    usineCreation.configurerBatterie(marqueBatterie, puissance);
    usineCreation.modifierMarqueVelo(marqueVelo);
    return VerifierResultat.USINE_MODIFIE;
  }
  
  /**
   * Ajoute une liste de vélos à la HashMap de vélos existante.
   * Si la liste est vide ou nulle, elle tente de récupérer les vélos à partir du fichier spécifié.
   *
   * @param nomFichier Le nom du fichier contenant les informations sur les vélos au format JSON.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat creerVelojson(String nomFichier) {
    ArrayList<Velo> liste =  new ArrayList<>();
    UsineFichier usineFichier = new UsineFichier(liste);
    liste = usineFichier.retourneVelos(nomFichier);
    if (liste == null || liste.size() == 0) {
      return usineFichier.recupererVelos(nomFichier);
    }
      
    VerifierResultat reponse = VerifierResultat.VELO_CREE;
    for (Velo velo : liste) {
      if (!GarageVelo.listeVelos.containsKey(velo.getNumeroSerie())) {
        listeVelos.put(velo.getNumeroSerie(), velo);
      } else {
        reponse = VerifierResultat.VELO_CREE_SANS_DOUBLON;
      }
    }
    return reponse;
  }

  
  /**
   * Méthode permettant de changer une roue spécifique sur un vélo, de vérifier sa conformité
   * et de mettre à jour la liste des vélos.
   *
   * @param numeroSerie     Le numéro de série du vélo.
   * @param roue            Le type de roue à changer (AV ou ARR).
   * @param marqueRoue      La nouvelle marque de la roue.
   * @param nouvelleLargeur La nouvelle largeur de la roue.
   * @param contientChambre La présence de chambre à air dans la roue.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat changerRoue(Integer numeroSerie, Roue roue, String marqueRoue,
      Integer nouvelleLargeur, boolean contientChambre) {
    if (numeroSerie == null || numeroSerie < 0) {
      return VerifierResultat.NUMERO_SERIE_INVALIDE;
    }

    Velo veloRecupere = listeVelos.get(numeroSerie);
    if (veloRecupere == null) {
      return VerifierResultat.VELO_INCONNU;
    }

    Pneu pneuModifier;
    Pneu pneuComparer;

    if (roue == null || (roue != Roue.AV && roue != Roue.ARR)) {
      return VerifierResultat.POSITION_INCONNU;
    }

    if (roue == Roue.AV) {
      pneuComparer = veloRecupere.getPneus()[1];
      pneuModifier = veloRecupere.getPneus()[0];
    } else {
      pneuComparer = veloRecupere.getPneus()[0];
      pneuModifier = veloRecupere.getPneus()[1];
    }
    if (pneuModifier == null) {
      return VerifierResultat.ERREUR_PNEUS; 
    }
    pneuModifier.setLargeur(nouvelleLargeur);
    pneuModifier.setMarque(marqueRoue);
    pneuModifier.setTubePresent(contientChambre);

    // Mettre à jour les pneus du vélo
    if (roue == Roue.AV) {
      veloRecupere.setPneus(pneuModifier, pneuComparer);
    } else {
      veloRecupere.setPneus(pneuComparer, pneuModifier);
    }

    // Vérifier la conformité du vélo modifié
    VerifierResultat resultat = VerifierVelo.estConforme(veloRecupere);
   
    listeVelos.put(numeroSerie, veloRecupere);
    return resultat; 
  }

  
  /**
   * Méthode permettant de changer la batterie d'un vélo, de vérifier sa conformité
   * et de mettre à jour la liste des vélos.
   *
   * @param numeroSerie      Le numéro de série du vélo.
   * @param puissanceBatterie La nouvelle puissance de la batterie.
   * @param marqueBatterie    La nouvelle marque de la batterie.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat changerBatterie(Integer numeroSerie, Integer puissanceBatterie,
      String marqueBatterie) {
    if (numeroSerie == null || numeroSerie < 0) {
      return VerifierResultat.NUMERO_SERIE_INVALIDE;
    }
    if (puissanceBatterie == null || puissanceBatterie < 0) {
      return VerifierResultat.PUISSANCE_BATTERIE_INVALIDE;
    }

    Velo veloRecupere = listeVelos.get(numeroSerie);
    if (veloRecupere == null) {
      return VerifierResultat.VELO_INCONNU;
    }
    if (listeVelos.get(numeroSerie).getBatterie() == null) {
      return VerifierResultat.BATTERIE_INEXISTANTE;
    }
    Batterie nouvelleBatterie = new Batterie(puissanceBatterie, marqueBatterie);
    veloRecupere.setBatterie(nouvelleBatterie);

    // Vérifier la conformité du vélo modifié
    VerifierResultat resultat = VerifierVelo.estConforme(veloRecupere);
    if (resultat == VerifierResultat.CONFORME) {
      listeVelos.put(numeroSerie, veloRecupere);
      return VerifierResultat.VELO_MODIFIE;
    } else {
      return resultat;
    }
  }
  
  /**
   * Méthode pour supprimer un vélo de la liste des vélos.
   *
   * @param numeroSerie Le numéro de série du vélo à supprimer.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat supprimerVelo(Integer numeroSerie) {
    if (numeroSerie == null || numeroSerie < 0) {
      return VerifierResultat.NUMERO_SERIE_INVALIDE;
    }

    if (!listeVelos.containsKey(numeroSerie)) {
      return VerifierResultat.VELO_INCONNU;
    }
    if (listeVelos.containsKey(numeroSerie)) {
      listeVelos.remove(numeroSerie);
      return VerifierResultat.VELO_SUPPRIME;
    } else {
      return VerifierResultat.VELO_INCONNU;
    }
  }
  
  /**
   * Crée des vélos à partir d'un fichier JSON et les ajoute à la liste de vélos du garage.
   *
   * @param nomFichier Le nom du fichier contenant les informations sur les vélos au format JSON.
   * @return Le résultat de l'opération.
   */
  public VerifierResultat creerVeloapartirfichier(String nomFichier) {
    UsineFichier usineFichier = new UsineFichier(new ArrayList<>()); // UsineFichier avec liste vide
    VerifierResultat resultat = usineFichier.recupererVelos(nomFichier); // Récup des vélos JSON
    if (resultat == VerifierResultat.VELO_RECUPERE 
        || resultat == VerifierResultat.VELO_RECUPERE_AVEC_NON_CONFORMITE) {
      ArrayList<Velo> velosRecuperes = usineFichier.retourneVelos(nomFichier); //  liste des vélos
      if (velosRecuperes != null) {
        for (Velo velo : velosRecuperes) {
          // Ajout des vélos à la liste de vélos du garage
          if (!listeVelos.containsKey(velo.getNumeroSerie())) {
            listeVelos.put(velo.getNumeroSerie(), velo);
          }
        }
      }
    }
    return resultat;
  }

  public static HashMap<Integer, Velo> recupListe() {
    return GarageVelo.listeVelos; 
  }
  
  public static Velo recupVelo() {
    return GarageVelo.veloSelectionne; 
  }
  
  public static UsineVelo recupUsine() {
    return GarageVelo.usineCreation; 
  }
  
  @Override
  public void applique(VisiteurElement unVisiteur) {
    // TODO Auto-generated method stub
    unVisiteur.agitSur(listeVelos);
  }
  
  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    if (observers.size() > 0) {
      System.out.println("Taille des observers :" + observers.size()); 
      for (Observer observer : observers) {
        observer.update(new ArrayList<>(listeVelos.values())); 
      }
    }

  }
  
  public int sizeObservers() {
    return this.observers.size(); 
  }

  
}
