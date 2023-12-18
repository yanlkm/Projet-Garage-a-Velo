package interfaces;


import elements.Velo;
import java.util.List;

/**
 * Creation d'observeurs.  
 *
 * @author yan
 *
 */
public interface Observer {

  void update(List<Velo> listeVelos);

}


