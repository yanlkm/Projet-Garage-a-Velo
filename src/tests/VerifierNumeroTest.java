package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import verifications.VerifierNumero;

/**
 * Classe de tests pour la classe VerifierNumero.
 */
public class VerifierNumeroTest {


  /**
  * Teste la création de numéros de série uniques.
  */
  @Test
  public void testCreerNumero() {
    Integer numero1 = VerifierNumero.creerNumero();
    Integer numero2 = VerifierNumero.creerNumero();

    assertNotNull(numero1);
    assertNotNull(numero2);
    assertNotEquals(numero1, numero2);
  }

  /**
  * Teste la vérification de l'unicité d'un numéro de série.
  */
  @Test
  public void testVerifierNumero() {
    VerifierNumero.creerNumero();
    Integer numero2 = VerifierNumero.creerNumero();
    System.out.println(numero2); 
    assertTrue(VerifierNumero.verifierNumero(numero2));
    assertFalse(VerifierNumero.verifierNumero(12345));
  }
}
