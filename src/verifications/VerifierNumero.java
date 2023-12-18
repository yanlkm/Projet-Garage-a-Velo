package verifications;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe de tests pour la classe VerifierVelo.
 */
public class VerifierNumero {

  private static ArrayList<Integer> listeNumeros; 

  /**
   * Crée un numéro de série unique en combinant l'heure actuelle et un nombre statique.
   *
   * @return Le numéro de série créé.
   */
  public static Integer creerNumero() {
    if (listeNumeros == null) {
      listeNumeros = new ArrayList<>();
    }

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
    String heureFormattee = now.format(formatter);

    String numeroSerieStr = heureFormattee + String.format("%04d", listeNumeros.size());

    Integer numeroCree = Integer.parseInt(numeroSerieStr);
    
    while (listeNumeros.contains(numeroCree)) {
      numeroCree++; 
    }
    listeNumeros.add(numeroCree);
    return numeroCree;
  }


  
  /**
   * Vérifie si un numéro de série est unique parmi les numéros de série déjà créés.
   *
   * @param numeroSerie Numéro de série à vérifier.
   * @return true si le numéro de série est unique, sinon false.
   */
  public static boolean verifierNumero(Integer numeroSerie) {
    if (listeNumeros == null) {
      listeNumeros = new ArrayList<Integer>(); 
    }
    return listeNumeros.contains(numeroSerie);
  }

  public static void ajouterNumero(Integer numeroSerie) {
    listeNumeros.add(numeroSerie); 
  }
}
