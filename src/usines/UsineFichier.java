package usines;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import elements.Batterie;
import elements.Pneu;
import elements.Velo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import reponses.VerifierResultat;
import verifications.VerifierNumero;
import verifications.VerifierVelo;




/**
 * Cette classe UsineFichier récupère des informations de vélos à partir d'un fichier JSON.
 */
public class UsineFichier {

  /** Liste statique pour stocker les vélos récupérés. */
  public static ArrayList<Velo> listeVelos;

  /** Constructeur initialisant la liste de vélos. */
  public UsineFichier(ArrayList<Velo> listeVelos) {
    UsineFichier.listeVelos = listeVelos;
  }

  /**
   * Récupère les vélos à partir d'un fichier JSON.
   *
   * @param nomFichier Nom du fichier contenant les informations des vélos au format JSON.
   * @return Le résultat de la récupération des vélos.
   */
  public VerifierResultat recupererVelos(String nomFichier) {
    Path path = Paths.get(nomFichier);  
    File file = new File(path.toString());
    StringBuilder jsonContent = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        jsonContent.append(line);
      }
    } catch (FileNotFoundException e) {
      return VerifierResultat.FICHIER_INTRROUVABLE;
    } catch (IOException e) {
      e.printStackTrace();
      return VerifierResultat.ERREUR_LECTURE_FICHIER;
    }

    try {
      // Récupérer les pneus
      Gson gson = new GsonBuilder()
          .registerTypeAdapter(Velo.class, (JsonDeserializer<Velo>) (json, typeOfT, context) 
              -> {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonObject pneuAvantObject = jsonObject.getAsJsonObject("pneuAvant");
            JsonObject pneuArriereObject = jsonObject.getAsJsonObject("pneuArriere");
            Pneu pneuAvant = null; 
            Pneu pneuArriere = null; 
            if (pneuAvantObject != null) {
              pneuAvant = new Pneu(
                        pneuAvantObject.get("marque").getAsString(),
                        pneuAvantObject.get("largeur").getAsInt(),
                        pneuAvantObject.get("contientChambre").getAsBoolean()
                    );
            }
            if (pneuArriereObject != null) {
              pneuArriere = new Pneu(
                pneuArriereObject.get("marque").getAsString(),
                pneuArriereObject.get("largeur").getAsInt(),
                pneuArriereObject.get("contientChambre").getAsBoolean()
             );
            }
            JsonObject batterieObject = jsonObject.getAsJsonObject("batterie");
            Batterie batterie = null; 
            if (batterieObject != null) {
              batterie = context.deserialize(batterieObject, 
                    Batterie.class);
            } else {
              batterie = null; 
            }

            Integer numeroSerie = jsonObject.get("numeroSerie").getAsInt();
            String model = jsonObject.get("model").getAsString();
            String marque = jsonObject.get("marque").getAsString();
            return new Velo(numeroSerie, model, marque, pneuAvant, pneuArriere, batterie);
          }).create();

      Velo[] velos = gson.fromJson(jsonContent.toString(), Velo[].class);

      VerifierResultat res = null; 

      if (velos != null) {
        for (Velo velo : velos) {
          if (!(VerifierVelo.estConforme(velo) == VerifierResultat.CONFORME)) {
            res =  VerifierVelo.estConforme(velo);
          }
          if (!estNumeroSerieUnique(velo.getNumeroSerie())) {
            listeVelos.add(velo); 
            VerifierNumero.ajouterNumero(velo.getNumeroSerie()); 
          } 
        }
        if (res == null) {
          return VerifierResultat.VELO_RECUPERE;
        }
        return VerifierResultat.VELO_RECUPERE_AVEC_NON_CONFORMITE;
        
      } else {
        return VerifierResultat.ERREUR_FORMAT_JSON;
      }
    } catch (JsonSyntaxException e) {
    
      return VerifierResultat.ERREUR_FORMAT_JSON;
    }
  }


  /**
   * Vérifie si un numéro de série est unique parmi les vélos déjà récupérés.
   *
   * @param numeroSerie Numéro de série à vérifier.
   * @return true si le numéro de série est unique, sinon false.
   */
  private boolean estNumeroSerieUnique(int numeroSerie) {
    return VerifierNumero.verifierNumero(numeroSerie);
  }
  
  /**
   * Vérifie si un numéro de série est unique parmi les vélos déjà récupérés.
   *
   * @param nomFichier fichier à vérifier.
   * @return la liste de velos la liste de velo si le numéro de série est unique.
   */  
  public ArrayList<Velo> retourneVelos(String nomFichier) {
    recupererVelos(nomFichier); 
    return UsineFichier.listeVelos; 

  }
}
