package elements;

import interfaces.Element;
import interfaces.VisiteurElement;

/**
 * Représente un pneu d'un vélo.
 */
public class Pneu extends Marque implements Element {

  /**
  * Les attributs d'un pneu. 
  */
  private Integer largeur; 
  private boolean contientChambre; 
  
  /**
   * Constructeur d'un pneu.
   *
   * @param marque marque
   * @param largeur largeur
   * @param contientChambre largeur
   */
  public Pneu(String marque, Integer largeur, boolean contientChambre) {
    super(marque);
    this.largeur = largeur; 
    this.contientChambre = contientChambre; 
  }


  public Integer getLargeur() {
    return this.largeur;
  }


  public void setLargeur(Integer largeur) {
    this.largeur = largeur;
  }


  public boolean contientChambre() {
    return this.contientChambre;
  }


  public void setTubePresent(boolean contientChambre) {
    this.contientChambre = contientChambre;
  }
  
  @Override
  public void applique(VisiteurElement unVisiteur) {
    // TODO Auto-generated method stub
    unVisiteur.agitSur(this);
  }  

}
