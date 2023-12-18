package verifications;

import elements.Pneu;
import elements.Velo;
import reponses.VerifierResultat;

/**
 * Classe qui vérifie la conformité d'un Vélo.
 */
public class VerifierVelo {


  static final Integer LARGEUR_MIN = 19;
  static final Integer LARGEUR_MAX = 30;
  
  /**
   * Compare si deux pneus sont compris dans les valeurs limites.
   *
   * @param pneuAvant   pneu avant du vélo
   * @param pneuArriere pneu arrière du vélo
   * @return Réponse représentant la réponse positive ou le cas d'erreur
   */
  public static VerifierResultat verifierValeurExtreme(Pneu pneuAvant, Pneu pneuArriere) {
    if (pneuAvant == null) {
      return VerifierResultat.PNEU_INCONNU;
    }
    if (pneuArriere == null) {
      return VerifierResultat.PNEU_INCONNU; 
    }
    Integer largeurPneuAvant = pneuAvant.getLargeur();
    Integer largeurPneuArriere = pneuArriere.getLargeur();

    if ((largeurPneuAvant > LARGEUR_MIN && largeurPneuAvant < LARGEUR_MAX)) {
      if ((largeurPneuArriere > LARGEUR_MIN && largeurPneuArriere < LARGEUR_MAX)) {
        return VerifierResultat.CONFORME;
      } else {
        return VerifierResultat.VALEUR_EXTREME_INCORRECTE;
      }
    } else {
      return VerifierResultat.VALEUR_EXTREME_INCORRECTE;
    }
  }

  /**
   * Vérifie si les pneus d'un vélo ont la même marque.
   *
   * @param pneuAvant   pneu avant du vélo
   * @param pneuArriere pneu arrière du vélo
   * @return Réponse représentant la réponse positive ou le cas d'erreur
   */
  public static VerifierResultat verifierMarquePneu(Pneu pneuAvant, Pneu pneuArriere) {
    if (pneuAvant.getMarque().equalsIgnoreCase(pneuArriere.getMarque())) {
      return VerifierResultat.CONFORME;
    } else {
      return VerifierResultat.MARQUE_PNEU_DIFFERENTE;
    }
  }

  /**
   * Vérifie si les pneus d'un vélo ont la même largeur.
   *
   * @param pneuAvant   pneu avant du vélo
   * @param pneuArriere pneu arrière du vélo
   * @return Réponse représentant la réponse positive ou le cas d'erreur
   */
  public static VerifierResultat verifierLargeurPneu(Pneu pneuAvant, Pneu pneuArriere) {
    if (pneuAvant.getLargeur().equals(pneuArriere.getLargeur())) {
      return VerifierResultat.CONFORME;
    } else {
      return VerifierResultat.LARGEUR_DIFFERENTE;
    }
  }

  /**
   * Vérifie si les pneus d'un vélo sont conformes aux spécifications.
   *
   * @return Réponse représentant la réponse positive ou le cas d'erreur
   */
  public static VerifierResultat estConforme(Velo veloExaminer) { 
    if (veloExaminer.getPneuAvant() == null || veloExaminer.getPneuArriere() == null) {
      return VerifierResultat.ERREUR_PNEUS; 
    }
    Pneu pneuAvant = veloExaminer.getPneuAvant();
    Pneu pneuArriere = veloExaminer.getPneuArriere();

    VerifierResultat valeurExtreme = verifierValeurExtreme(pneuAvant, pneuArriere);
    if (valeurExtreme == VerifierResultat.CONFORME) {
      VerifierResultat marquePneu = verifierMarquePneu(pneuAvant, pneuArriere);
      if (marquePneu == VerifierResultat.CONFORME) {
        return verifierLargeurPneu(pneuAvant, pneuArriere);
      } else {
        return marquePneu;
      }
    } else {
      return valeurExtreme;
    }
  }
}
