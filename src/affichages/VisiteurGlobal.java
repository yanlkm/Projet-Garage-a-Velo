package affichages;



import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import interfaces.VisiteurElement;
import java.util.HashMap;

/**
 * Classe représentant un visiteur pour afficher les informations des éléments visités.
 */
public class VisiteurGlobal implements VisiteurElement {

  /**
   * Affiche les informations de tous les vélos présents dans le garage.
   *
   * @param listeVelos HashMap contenant les vélos indexés par leur numéro de série
   */
  @Override
  public void agitSur(HashMap<Integer, Velo> listeVelos) {
    System.out.println("Affichage des vélos dans le garage :");
    for (Integer numeroSerie : listeVelos.keySet()) {
      Velo velo = listeVelos.get(numeroSerie);
      System.out.println();
      System.out.println("Numéro de série : " + numeroSerie 
          + " | Vélo -- marque : " + velo.getMarque() + " // modèle : " + velo.getModel());
    }
    System.out.println(); 
    System.out.println("Fin de l'affichage des vélos.");
  }

  /**
   * Ne traite pas l'affichage individuel du vélo ici, utilisé pour le parcours des éléments.
   *
   * @param velo Instance de la classe Velo
   */
  @Override
  public void agitSur(Velo velo) {
    // Ne traite pas l'affichage individuel du vélo ici, utilisé pour le parcours des éléments
  }

  /**
   * Pas nécessaire pour l'affichage des pneus isolés ici.
   *
   * @param pneu Instance de la classe Pneu
   */
  @Override
  public void agitSur(Pneu pneu) {
    // Pas nécessaire pour l'affichage des pneus isolés ici
  }

  /**
   * Pas nécessaire pour l'affichage de la batterie isolée ici.
   *
   * @param batterie Instance de la classe Batterie
   */
  @Override
  public void agitSur(Batterie batterie) {
    // Pas nécessaire pour l'affichage de la batterie isolée ici
  }
}
