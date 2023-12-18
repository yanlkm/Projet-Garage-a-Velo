package interfaces;

/**
 * Interface d'obersavble.
 *
 * @author yan
 *
 */
public interface Observable {

  void addObserver(Observer observer);
  
  void removeObserver(Observer observer);
  
  void notifyObservers();
}
