package tests;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import facade.GarageVelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import reponses.Roue;
import reponses.VerifierResultat;

/**
 * Classe de tests unitaires pour la classe GarageVelo.
 */
public class GarageVeloTest {
  GarageVelo garageVelo;
  
  /**
   * Méthode pour itérer sur la liste de numéro de série.
   */
  public void itererSurNumerosSerie() {
    if (GarageVelo.recupListe() == null) {
      // Gérer le cas où la HashMap est null ou vide
      return;
    }

    for (Integer numeroSerie : GarageVelo.recupListe().keySet()) {
      // Faites quelque chose avec chaque numéro de série
      System.out.println("Numéro de série : " + numeroSerie);
    }
  }
  
  /** 
   * Creer le setup.
   */
  @Before
  public void setUp() {
    garageVelo = new GarageVelo();
    garageVelo.creerVelo("Marque", "Model");
    garageVelo.creerVelo("Marque1", "Model1");
    garageVelo.creerVelo("Marque2", "Model2");
    garageVelo.creerVelo("Marque3", "Model3");
    garageVelo.creerVelo("Marque4", "Model4");
    itererSurNumerosSerie();
  }

  @Test
  public void testConstructor() {
    assertNotNull(GarageVelo.recupListe());
  
  }
  
  @Test
  public void testConfigurerUsine() {
    VerifierResultat resultat = garageVelo.configurerUsine("MarqueVelo", 
        "MarqueBatterie", "MarquePneu", 100, 20, true);
    assertEquals(VerifierResultat.USINE_MODIFIE, resultat);
    assertEquals((int) GarageVelo.recupUsine().getLargeurPneu(), 20);
    assertEquals((int) GarageVelo.recupUsine().getPuissanceBatterie(), 100);
    assertEquals((String) GarageVelo.recupUsine().getMarqueBatterie(), "MarqueBatterie");
    assertEquals((String) GarageVelo.recupUsine().getMarqueVelo(), "MarqueVelo");
    assertEquals((String) GarageVelo.recupUsine().getMarquePneu(), "MarquePneu");
  }  
  
  @Test
  public void testCreerVelo() {
    assertEquals(VerifierResultat.CONFORME, garageVelo.creerVelo("Marque", "Model"));
    assertEquals(VerifierResultat.MARQUE_INCONNU, garageVelo.creerVelo(null, "Model"));
    assertEquals(VerifierResultat.MODEL_INCONNU, garageVelo.creerVelo("Marque", null));
  }

  @Test
  public void testChangerRoue() {
    System.out.println("Vélos contenus (roue)");
    itererSurNumerosSerie();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
    String heureFormattee = now.format(formatter);

    String numeroSerieStr = heureFormattee + String.format("%04d", 34);
    Integer numCre = Integer.parseInt(numeroSerieStr);
    System.out.println(" changer roue :" + numCre);
    VerifierResultat verif1 = garageVelo.changerRoue(numCre, Roue.AV, "Marque Pneu1", 25, true);
    assertEquals(VerifierResultat.CONFORME, verif1); 
    boolean reponse = GarageVelo.recupListe().get(numCre).getPneus()[0].contientChambre(); 
    assertEquals(true, reponse); 
    VerifierResultat verif2 = garageVelo.changerRoue(null, Roue.AV, "Marque", 25, true);
    assertEquals(VerifierResultat.NUMERO_SERIE_INVALIDE, verif2);
    VerifierResultat verif3 = garageVelo.changerRoue(87, Roue.AV, "Marque", 25, true);
    assertEquals(VerifierResultat.VELO_INCONNU, verif3);
  }

  @Test
  public void testChangerBatterie() {
    System.out.println("Vélos contenus (batterie)");
    itererSurNumerosSerie();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
    String heureFormattee = now.format(formatter);

    String numeroSerieStr = heureFormattee + String.format("%04d", 14);
    Integer numeroCree = Integer.parseInt(numeroSerieStr);
    VerifierResultat verif1 = garageVelo.changerBatterie(numeroCree, 100, "Batman");
    assertEquals(VerifierResultat.VELO_MODIFIE, verif1);
    Integer reponse = GarageVelo.recupListe().get(numeroCree).getBatterie().getPuissance();
    assertEquals((Integer) 100, reponse);
    assertEquals("Batman", GarageVelo.recupListe().get(numeroCree).getBatterie().getMarque());
    VerifierResultat verif2 = garageVelo.changerBatterie(null, 100, "MarqueBatterie");
    assertEquals(VerifierResultat.NUMERO_SERIE_INVALIDE, verif2);
    VerifierResultat verif3 = garageVelo.changerBatterie(11220002, -100, "MarqueBatterie");
    assertEquals(VerifierResultat.PUISSANCE_BATTERIE_INVALIDE, verif3);
    VerifierResultat verif4 = garageVelo.changerBatterie(787, 100, "MarqueBatterie");
    assertEquals(VerifierResultat.VELO_INCONNU, verif4);
  }

  @Test
  public void testSupprimerVelo() {
    System.out.println("Vélos contenus (sup)");
    itererSurNumerosSerie();  
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
    String heureFormattee = now.format(formatter);

    String numeroSerieStr = heureFormattee + String.format("%04d", 12);
    Integer numeroCree = Integer.parseInt(numeroSerieStr);
    assertEquals(VerifierResultat.VELO_SUPPRIME, garageVelo.supprimerVelo(numeroCree));
    assertEquals(VerifierResultat.NUMERO_SERIE_INVALIDE, garageVelo.supprimerVelo(null));
    assertEquals(VerifierResultat.VELO_INCONNU, garageVelo.supprimerVelo(10));
  }
  

  @Test
  public void testCreerVelojson() {
    VerifierResultat resultat = garageVelo.creerVelojson("fichiers_json/velos_valides.json");
    assertEquals(VerifierResultat.VELO_CREE, resultat);
  }
  
}
