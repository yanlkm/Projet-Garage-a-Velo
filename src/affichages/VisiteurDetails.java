package affichages;



import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import interfaces.VisiteurElement;
import java.util.HashMap;

/**
 * Classe implémentant l'interface VisiteurElement pour afficher des détails sur les éléments.
 */
public class VisiteurDetails implements VisiteurElement {

  /**
  * Affiche les détails d'un vélo spécifique, y compris ses composants.
  *
  * @param velo Le vélo pour lequel afficher les détails.
  */
  @Override
  public void agitSur(Velo velo) {
    System.out.println("Informations sur le vélo:");
    if (velo == null) {
      System.out.println("Vélo non défini !");
    } else {
      // Affichage des informations générales sur le vélo
      System.out.println("Numéro de série: " + velo.getNumeroSerie());
      System.out.println("Modèle: " + velo.getModel());
      System.out.println("Marque: " + velo.getMarque());

      System.out.println("Pneu avant:");
      agitSur(velo.getPneuAvant());

      System.out.println("Pneu arrière:");
      agitSur(velo.getPneuArriere());

      System.out.println("Batterie du vélo:");
      agitSur(velo.getBatterie());
    }
  }

  /**
  * Affiche les détails d'un pneu y compris la marque la largeur et la présence de chambre à air.
  *
  * @param pneu Le pneu pour lequel afficher les détails.
  */
  @Override
  public void agitSur(Pneu pneu) {
    // Affichage des détails du pneu avant
    if (pneu != null) {
      System.out.println("Marque: " + pneu.getMarque());
      System.out.println("Largeur: " + pneu.getLargeur());
      System.out.println("Contient chambre à air: " + pneu.contientChambre());
    } else {
      System.out.println("Pneu non défini.");
    }
  }

  /**
  * Affiche les détails d'une batterie, y compris la marque et la puissance.
  *
  * @param batterie La batterie pour laquelle afficher les détails.
  */
  @Override
  public void agitSur(Batterie batterie) {
    if (batterie != null) {
      System.out.println("Batterie:");
      System.out.println("Marque: " + batterie.getMarque());
      System.out.println("Puissance: " + batterie.getPuissance());
    } else {
      System.out.println("Batterie non définie.");
    }
  }

  /**
  * Ne traite pas l'affichage global des vélos.
  *
  * @param listeVelos La liste de vélos pour laquelle aucun traitement n'est effectué.
  */
  @Override
  public void agitSur(HashMap<Integer, Velo> listeVelos) {
    // Ne traite pas l'affichage global des vélos
  }
}
