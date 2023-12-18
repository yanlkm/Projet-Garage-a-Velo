package interfaces;


import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import java.util.HashMap;

/**
 * VisiteurElement est une interface definissant chaque type d'objet que le visiteur peut visiter.
 *
 * @author yan
 * 
 *
 */
public interface VisiteurElement {

  void agitSur(HashMap<Integer, Velo> listeVelos); 
  
  void agitSur(Velo velo); 
  
  void agitSur(Pneu pneu); 
  
  void agitSur(Batterie batterie); 

}
