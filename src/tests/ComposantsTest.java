package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import elements.Batterie;
import elements.Marque;
import elements.Pneu;
import elements.Velo;
import org.junit.Test;




/**
 * Classe de tests pour les composants : Velo, Pneu, Batterie et Marque.
 */
public class ComposantsTest {



  /**
  * Teste les fonctionnalités de la classe Marque.
  */
  @Test
  public void testMarque() {
    Marque marque = new Marque("Marque");

    assertEquals("Marque", marque.getMarque());

    marque.setMarque("NewMarque");

    assertEquals("NewMarque", marque.getMarque());
  }
  
  
  
  /**
   * Teste les fonctionnalités de la classe Pneu.
   */
  @Test
  public void testPneu() {
    Pneu pneu = new Pneu("Michelin", 25, true);

    assertEquals("Michelin", pneu.getMarque());
    assertEquals(25, pneu.getLargeur().intValue());
    assertTrue(pneu.contientChambre());

    pneu.setMarque("NewMichelin");
    pneu.setLargeur(30);
    pneu.setTubePresent(false);

    assertEquals("NewMichelin", pneu.getMarque());
    assertEquals(30, pneu.getLargeur().intValue());
    assertFalse(pneu.contientChambre());
  }

  /**
   * Teste les fonctionnalités de la classe Batterie.
   */
  @Test
  public void testBatterie() {
    Batterie batterie = new Batterie(100, "MarqueBatterie");

    assertEquals(100, batterie.getPuissance().intValue());
    assertEquals("MarqueBatterie", batterie.getMarque());

    batterie.setPuissance(80);
    batterie.setMarque("NewMarqueBatterie");

    assertEquals(80, batterie.getPuissance().intValue());
    assertEquals("NewMarqueBatterie", batterie.getMarque());
  }
  
  /**
   * Teste les fonctionnalités de la classe Velo.
   */
  @Test
  public void testVelo() {
    Velo velo = new Velo(12345, "Model", "Marque",
            new Pneu("Michelin", 25, true),
            new Pneu("Michelin", 25, true),
            new Batterie(100, "MarqueBatterie"));

    assertEquals(12345, velo.getNumeroSerie().intValue());
    assertEquals("Model", velo.getModel());

    velo.setNumeroSerie(54321);
    velo.setModel("NewModel");

    assertEquals(54321, velo.getNumeroSerie().intValue());
    assertEquals("NewModel", velo.getModel());

    assertNotNull(velo.getPneus());
    assertNotNull(velo.getPneuAvant());
    assertNotNull(velo.getPneuArriere());

    assertEquals("Michelin", velo.getPneuAvant().getMarque());
    assertEquals(25, velo.getPneuAvant().getLargeur().intValue());
    assertTrue(velo.getPneuAvant().contientChambre());

    assertNotNull(velo.getBatterie());

    assertEquals(100, velo.getBatterie().getPuissance().intValue());
    assertEquals("MarqueBatterie", velo.getBatterie().getMarque());

    velo.setBatterie(new Batterie(80, "NewMarqueBatterie"));

    assertEquals(80, velo.getBatterie().getPuissance().intValue());
    assertEquals("NewMarqueBatterie", velo.getBatterie().getMarque());
  }



}
