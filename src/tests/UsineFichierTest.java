package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import reponses.VerifierResultat;
import usines.UsineFichier;

/**
 * Classe de tests pour la classe UsineFichier.
 */
public class UsineFichierTest {

  private UsineFichier usineFichier;

  @Before
  public void setUp() {
    usineFichier = new UsineFichier(new ArrayList<>());
  }

  /**
  * Test : Fichier introuvable.
  */
  @Test
  public void testRecupererVelos_FileNotFound() {
    assertEquals(VerifierResultat.FICHIER_INTRROUVABLE,
        usineFichier.recupererVelos("fichiers_json/fichier_inexistant.json"));
  }

  /**
  * Test : Format JSON invalide.
  */
  @Test
  public void testrecupererVelos_invalidformatjson() {
    assertEquals(VerifierResultat.ERREUR_FORMAT_JSON,
        usineFichier.recupererVelos("fichiers_json/invalid_format.json"));
  }

  /**
  * Test : Numéro de série non unique dans le JSON.
  */
  @Test
  public void testRecupererVelos_NumeroSerieNonUnique() {
    // Créer un fichier JSON contenant des numéros de série non uniques
    assertEquals(VerifierResultat.ERREUR_FORMAT_JSON,
        usineFichier.recupererVelos("fichiers_json/numeros_non_uniques.json"));
  }

  /**
  * Test : JSON valide.
  */
  @Test
  public void testRecupererVelos_withvalidjson() {
    // Créer un fichier JSON valide
    assertEquals(VerifierResultat.VELO_RECUPERE, usineFichier
        .recupererVelos("fichiers_json/velos_valides.json"));
    // Vérifier que la liste de vélos n'est pas vide
    assertFalse(UsineFichier.listeVelos.isEmpty());
    
  }


}
