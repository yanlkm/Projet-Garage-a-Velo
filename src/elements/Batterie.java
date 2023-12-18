package elements;

import interfaces.Element;
import interfaces.VisiteurElement;

/**
 * Représente un pneu d'un vélo.
 */
public class Batterie extends Marque implements Element {

  /**
  * Les attributs d'une batterie. 
  */
  private Integer puissance; 
 
  public Batterie(Integer puissance, String marque) {
    super(marque); 
    this.puissance = puissance; 
  }

  public Integer getPuissance() {
    return puissance;
  }

  public void setPuissance(Integer puissance) {
    this.puissance = puissance;
  }

  @Override
  public void applique(VisiteurElement unVisiteur) {
    // TODO Auto-generated method stub
    unVisiteur.agitSur(this);
  }
  
}
