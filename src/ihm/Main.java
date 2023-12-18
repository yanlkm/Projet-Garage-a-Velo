package ihm;

import facade.GarageVelo;

/**
 * Classe principale contenant le point d'entrée du programme.
 */
public class Main {

  /**
   * Méthode principale pour lancer l'interface utilisateur.
   *
   * @param args Arguments du programme.
   */

  public static void main(String[] args) {
    GarageVelo garage = new GarageVelo();
    Controleur controle = new Controleur(garage);
    controle.afficherListevelos();
  }
}
