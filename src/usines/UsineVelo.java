package usines;
import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import verifications.VerifierNumero;

/**
 * Usine qui crée automatiquement un vélo.
 */
public class UsineVelo {

  /**
  * Les attributs pour la création d'un velo.
  */

  private String marqueBatterie;
  private String marqueVelo;
  private String marquePneu; 
  private Integer puissanceBatterie; 
  private Integer largeurPneu;
  private boolean contientChambre; 

  
  UsineVelo(String marqueVelo) {
    this.marqueBatterie = "Power Battery"; 
    this.marqueVelo = marqueVelo; 
    this.puissanceBatterie = 40;
    this.contientChambre = false; 
    this.largeurPneu = 25;
    this.marquePneu = "Marque Pneu1"; 
  
  }
  
  public UsineVelo() {
    this.marqueBatterie = "Power Battery"; 
    this.marqueVelo = "Velozef by Yan";
    this.puissanceBatterie = 40;
    this.contientChambre = false; 
    this.largeurPneu = 25;
    this.marquePneu = "Marque Pneu1"; 

  }  
  
  /**
  * Crée un velo automatiquement en fonction des caractéristiques des roues et du model voulu.
  *
  * @param model model voulu par l'utilisateur
  * @return retourne un velo créé
  */
  public Velo creerVelo(String model) {
    Pneu pneuAvantVelo = new Pneu(marquePneu, largeurPneu, contientChambre);
    Pneu pneuArriereVelo = new Pneu(marquePneu, largeurPneu, contientChambre);
    Batterie batterieVelo = new Batterie(puissanceBatterie, marqueBatterie);
    Integer numeroSerie = VerifierNumero.creerNumero();
    Velo velo = new Velo(numeroSerie, model, marqueVelo, pneuAvantVelo, 
        pneuArriereVelo, batterieVelo); 
    return velo; 

  }
 
  /**
  *Configure les paramètres de la batterie à mettre dans le vélo.
  *
  * @param puissanceBatterie puissance de batterie voulue par l'utilisateur
  * @param marqueBatterie marque voulu par l'utilisateur 
  */
  public void configurerBatterie(String marqueBatterie, Integer puissanceBatterie) {
    this.marqueBatterie = marqueBatterie;
    this.puissanceBatterie = puissanceBatterie;
    
  }

  /**
  *Configure les paramètres des pneus à mettre dans le vélo.
  *
  * @param marquePneu marque de la batterie voulue par l'utilisateur
  * @param largeurPneu largeur du pneu voulue par l'utilisateur 
  * @param contientChambre chambre a air ou non voulu par l'utilisateur 
  */  
  public void configurerPneu(String marquePneu, Integer largeurPneu, boolean contientChambre) {
    this.marquePneu = marquePneu;
    this.largeurPneu = largeurPneu;  
    this.contientChambre = contientChambre; 
  }

  public void modifierContientChambre(boolean contientChambre) {
    this.contientChambre = contientChambre; 
  }
  
  public void modifierMarqueVelo(String marqueVelo) {
    this.marqueVelo = marqueVelo; 
  }
  

  public void modifierPuissanceBatterie(Integer puissanceBatterie) {
    this.puissanceBatterie = puissanceBatterie;
  }

  public void modifierLargeurPneu(Integer largeurPneu) {
    this.largeurPneu = largeurPneu;
  }
  
  
  public String getMarqueVelo() {
    return marqueVelo;
  } 
  
  public Integer getPuissanceBatterie() {
    return puissanceBatterie;
  }
  
  public String getMarquePneu() {
    return marquePneu; 
  }

  public Integer getLargeurPneu() {
    return largeurPneu;
  }
  
  public String getMarqueBatterie() {
    return marqueBatterie;
  }
  
  public boolean contientChambre() {
    return this.contientChambre;
  }

}
