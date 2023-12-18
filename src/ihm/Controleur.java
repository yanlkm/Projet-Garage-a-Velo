package ihm;

import elements.Velo;
import facade.GarageVelo;
import vues.VueListevelo;
import vues.VueVelo;

/**
 * Controleur de l'ihm.
 * Gère les interactions entre la vue et le modèle.
 *
 * @author yan
 */
public class Controleur {

  static GarageVelo garage;

  /**
   * Constructeur du Controleur.
   *
   * @param garage Instance de GarageVelo.
   */
  public Controleur(GarageVelo garage) {
    Controleur.garage = garage;
  }

  /**
   * Affiche la liste des vélos.
   */
  public void afficherListevelos() {
    new VueListevelo(garage);
  }

  /**
   * Affiche la vue détaillée d'un vélo spécifique.
   *
   * @param velo Vélo à afficher en détail.
   */
  public static void afficherVelo(Velo velo) {
    new VueVelo(garage, velo);
  }
}
