package tests;

import static org.junit.Assert.assertEquals;

import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import org.junit.Test;
import reponses.VerifierResultat;
import verifications.VerifierVelo;

/**
 * Classe de tests pour la classe VerifierVelo.
 */
public class VerifierVeloTest {

  /**
   * Teste la méthode ValeurExtreme() lorsque les pneus ont la même largeur et sont conformes.
   */
  @Test
  public void testVerifierValeurExtreme_Conforme() {
    Pneu pneuAvant = new Pneu("MarqueA", 25, true);
    Pneu pneuArriere = new Pneu("MarqueA", 25, true);

 

    assertEquals(VerifierResultat.CONFORME,
        VerifierVelo.verifierValeurExtreme(pneuAvant, pneuArriere));
  }

  /**
   * Teste la méthode verifierValeurExtreme() lorsque la largeur d'un pneu est incorrecte.
   */
  @Test
  public void testVerifierValeurExtreme_LargeurInferieureIncorrect() {
    Pneu pneuAvant = new Pneu("MarqueB", 18, true);
    Pneu pneuArriere = new Pneu("MarqueB", 25, true);



    assertEquals(VerifierResultat.VALEUR_EXTREME_INCORRECTE,
        VerifierVelo.verifierValeurExtreme(pneuAvant, pneuArriere));
  }
  
  /**
   * Teste la méthode verifierValeurExtreme() lorsque la largeur d'un pneu est incorrecte.
   */
  @Test
  public void testVerifierValeurExtreme_LargeurSuperieureIncorrecte() {
    Pneu pneuAvant = new Pneu("MarqueB", 40, true);
    Pneu pneuArriere = new Pneu("MarqueB", 25, true);

    assertEquals(VerifierResultat.VALEUR_EXTREME_INCORRECTE,
        VerifierVelo.verifierValeurExtreme(pneuAvant, pneuArriere));
  }
  
  /**
   * Teste la méthode verifierValeurExtreme() lorsque le pneu avant est null.
   */
  @Test
  public void testVerifierValeurExtreme_PneuAvantNull() {
    Pneu pneuArriere = new Pneu("MarqueC", 20, true);

    

    assertEquals(VerifierResultat.PNEU_INCONNU, 
        VerifierVelo.verifierValeurExtreme(null, pneuArriere));
  }

  /**
   * Teste la méthode verifierValeurExtreme() lorsque le pneu arrière est null.
   */
  @Test
  public void testVerifierValeurExtreme_PneuArriereNull() {
    Pneu pneuAvant = new Pneu("MarqueD", 22, true);



    assertEquals(VerifierResultat.PNEU_INCONNU,
        VerifierVelo.verifierValeurExtreme(pneuAvant, null));
  }

  // Autres tests pour les autres méthodes...

  /**
   * Teste la méthode estConforme() avec un vélo conforme.
   */
  @Test
  public void testEstConforme_Conforme() {
    Pneu pneuAvant = new Pneu("MarqueH", 25, true);
    Pneu pneuArriere = new Pneu("MarqueH", 25, true);
    Velo velo = new Velo(123, "ModèleA", "MarqueH", pneuAvant,
        pneuArriere, new Batterie(100, "MarqueX"));

    

    assertEquals(VerifierResultat.CONFORME, VerifierVelo.estConforme(velo));
  }
}
