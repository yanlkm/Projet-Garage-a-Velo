package vues;

import elements.Pneu;
import elements.Velo;
import facade.GarageVelo;
import interfaces.Observer;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import reponses.Roue;
import reponses.VerifierResultat;
import verifications.VerifierVelo;

/**
 * Classe représentant la vue listant les composant d'un vélo.
 */
public class VueVelo extends JFrame implements Observer {


  private static final long serialVersionUID = 1L;
  private JLabel numeroSerieLabel;
  private JLabel modeleLabel;
  private JLabel marqueLabel;
  private JLabel pneuAvantLabel;
  private JLabel pneuArriereLabel;
  private JLabel conformitePneusLabel;
  private JLabel marqueBatterieLabel;
  private JLabel puissanceBatterieLabel;

  private JTextField marquePneu;
  private JTextField largeurPneu;
  private JTextField marqueBatterieField;
  private JTextField puissanceField;

  JLabel labelMarquePneu;
  JLabel labelMarqueBatterie;
  JLabel labelPuissance;
  JLabel labelLargeurpneu;

  private JButton modifierButton;

  @SuppressWarnings("unused")
  private static GarageVelo garage;

  public Velo velo;

  static boolean selection = false;

  /**
  * Vue pour afficher les détails d'un vélo dans une fenêtre JFrame.
  * Permet également de modifier certains détails du vélo.
  *
  * @param garage L'instance de GarageVelo liée à cette vue.
  * @param velo   Le vélo à afficher dans cette vue.
  */
  public VueVelo(GarageVelo garage, Velo velo) {
    VueVelo.garage = garage;
    garage.addObserver(this);
    this.velo = velo;

    numeroSerieLabel = new JLabel("Numéro de série : ");
    modeleLabel = new JLabel("Modèle : ");
    marqueLabel = new JLabel("Marque : ");
    pneuAvantLabel = new JLabel("Pneu avant : ");
    pneuArriereLabel = new JLabel("Pneu arrière : ");
    conformitePneusLabel = new JLabel("Conformité des pneus : ");
    marqueBatterieLabel = new JLabel("Marque Batterie : ");
    puissanceBatterieLabel = new JLabel("Puissance Batterie : ");

    labelMarquePneu = new JLabel("Marque pneu : ");
    labelMarqueBatterie = new JLabel("Marque batterie : ");
    labelPuissance = new JLabel("Puissance batterie : ");
    labelLargeurpneu = new JLabel("Largeur pneu :");

    // Initialisation et configuration de la fenêtre JFrame
    setTitle("Détails du vélo");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    
    // Utilisation d'un JPanel avec GridBagLayout pour organiser les composants
    JPanel panel = new JPanel(new GridBagLayout());
    panel.add(numeroSerieLabel, gbc);
    gbc.gridy++;
    panel.add(modeleLabel, gbc);
    gbc.gridy++;
    panel.add(marqueLabel, gbc);
    gbc.gridy++;
    panel.add(pneuAvantLabel, gbc);
    gbc.gridy++;
    panel.add(pneuArriereLabel, gbc);
    gbc.gridy++;
    panel.add(marqueBatterieLabel, gbc);
    gbc.gridy++;
    panel.add(puissanceBatterieLabel, gbc);
    gbc.gridy++;
    panel.add(conformitePneusLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;

    panel.add(new JLabel(), gbc); // Espacement

    marquePneu = new JTextField(10);
    largeurPneu = new JTextField(10);
    marqueBatterieField = new JTextField(10);
    puissanceField = new JTextField(10);

    // Liste déroulante pour choisir entre AV (avant) et AR (arrière)
    String[] choixPositionRoue = { "AV", "AR" };
    JComboBox<String> positionRoueDropdown = new JComboBox<>(choixPositionRoue);
    panel.add(new JLabel("Position de la roue : "), gbc);
    gbc.gridy++;
    panel.add(positionRoueDropdown, gbc);
    gbc.gridy++;

    panel.add(labelMarquePneu, gbc);
    gbc.gridy++;

    panel.add(marquePneu, gbc);
    gbc.gridy++;

    panel.add(labelLargeurpneu, gbc);
    gbc.gridy++;

    panel.add(largeurPneu, gbc);
    gbc.gridy++;

    // Liste déroulante pour choisir entre OUI et NON pour contientChambre
    String[] choixContientChambre = { "OUI", "NON" };
    JComboBox<String> contientChambreDropdown = new JComboBox<>(choixContientChambre);
    panel.add(new JLabel("Contient chambre : "), gbc);
    gbc.gridy++;
    panel.add(contientChambreDropdown, gbc);
    gbc.gridy++;

    panel.add(labelMarqueBatterie, gbc);
    gbc.gridy++;
    panel.add(marqueBatterieField, gbc);
    gbc.gridy++;

    panel.add(labelPuissance, gbc);
    gbc.gridy++;
    panel.add(puissanceField, gbc);
    gbc.gridy++;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridy++;
    gbc.fill = GridBagConstraints.NONE;
    modifierButton = new JButton("Modiier");
    panel.add(modifierButton, gbc);

    modifierButton.addActionListener(e -> {
      if (this.velo != null) {
        Velo veloSelectionne = this.velo;

        boolean champsRemplisPneu = !marquePneu.getText().isEmpty();
        boolean champsRemplisBatterie = (!marqueBatterieField.getText().isEmpty()
             && !puissanceField.getText().isEmpty());

        boolean bonTypeDonneespuissance = verifierBonTypeDonnees(puissanceField.getText());
        boolean bonTypeDonneeslargeur = verifierBonTypeDonnees(largeurPneu.getText());

        if (veloSelectionne != null && champsRemplisBatterie && bonTypeDonneespuissance) {
          garage.changerBatterie(veloSelectionne.getNumeroSerie(), 
                  Integer.parseInt(puissanceField.getText()), marqueBatterieField.getText());

          afficherDetailsVelo(veloSelectionne);
          garage.notifyObservers();
        } else if (veloSelectionne != null && champsRemplisPneu && bonTypeDonneeslargeur) {
          // Choisir la position de la roue en fonction de la sélection de la liste
          Roue positionRoue = positionRoueDropdown.getSelectedItem()
              .equals("AV") ? Roue.AV : Roue.ARR;

          // Récupérer la sélection pour contientChambre
          boolean contientChambre = contientChambreDropdown.getSelectedItem().equals("OUI");

          // Appeler la méthode changerRoue de GarageVelo
          VerifierResultat res = garage.changerRoue(veloSelectionne.getNumeroSerie(), positionRoue,
               marquePneu.getText(), Integer.parseInt(largeurPneu.getText()),
               contientChambre);

          // Mettre à jour l'affichage si le résultat est conforme

          if (res != VerifierResultat.ERREUR_PNEUS) {
            afficherDetailsVelo(veloSelectionne);
            garage.notifyObservers();
          } else {
            JOptionPane.showMessageDialog(this, "Erreurs aux pneus, veuillez-supprimer le vélo");

          }

        } else {
          JOptionPane.showMessageDialog(this, "Veuillez remplir tous"
              + " les champs avec les bonnes valeurs.");
        }
      }
    });

    // Ajout du panel à la fenêtre JFrame
    add(panel);
    setSize(900, 500); // Taille de la fenêtre (largeur x hauteur)
    setVisible(true);
    afficherDetailsVelo(velo);
  }
  
  /**
   * Méthode pour vérifier si la valeur de la puissance est un entier valide.
   *
   * @param text Texte à vérifier.
   * @return Vrai si la puissance est un entier valide, sinon faux.
   */
  private boolean verifierBonTypeDonnees(String text) {
    try {
      if (Integer.parseInt(text) > 0) {
        return true;
      } else {
        return false;
      }

    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Méthode appelée lors de la mise à jour de la liste des vélos.
   *
   * @param listeVelos Liste des vélos mise à jour.
   */
  @Override
  public void update(List<Velo> listeVelos) {

    System.out.println("Appel update sur vue vélo");
    if (listeVelos.contains(velo)) {
      afficherDetailsVelo(velo);
    } else {
      VueVelo.garage = null;
      this.velo = null;
      afficherDetailsVelo(null);
    }

  }

  /**
   * Affiche les détails d'un vélo donné.
   *
   * @param velo Vélo dont les détails doivent être affichés.
   */
  private void afficherDetailsVelo(Velo velo) {

    if (velo == null) {
      Container contentPane = getContentPane();
      contentPane.removeAll(); // Supprime tous les composants du conteneur
      JLabel messageSupprime = new JLabel("Vélo supprimé ou inexistant."
          + " Veuillez fermer la fenêtre.");
      messageSupprime.setForeground(Color.RED);
      messageSupprime.setHorizontalAlignment(SwingConstants.CENTER);
      contentPane.add(messageSupprime);

    } else {
      numeroSerieLabel.setText("Numéro de série : " + velo.getNumeroSerie());
      modeleLabel.setText("Modèle : " + velo.getModel());
      marqueLabel.setText("Marque : " + velo.getMarque());
      pneuAvantLabel.setText("Pneu avant : " + getDetailsPneu(velo.getPneuAvant()));
      pneuArriereLabel.setText("Pneu arrière : " + getDetailsPneu(velo.getPneuArriere()));
      marqueBatterieLabel.setText("Marque Batterie : " + velo.getBatterie().getMarque());
      puissanceBatterieLabel.setText("Puissance Batterie : " + velo.getBatterie().getPuissance());

      VerifierResultat resultat = VerifierVelo.estConforme(velo);

      if (resultat == VerifierResultat.CONFORME) {
        conformitePneusLabel.setText("Conformité des pneus : OK");
        conformitePneusLabel.setForeground(Color.GREEN); // Couleur verte pour conformité
      } else {
        conformitePneusLabel.setText("Conformité des pneus : Non conforme");
        conformitePneusLabel.setForeground(Color.RED); // Couleur rouge pour non conformité
      }
    }
    revalidate(); // Met à jour l'affichage
    repaint(); // Redessine l'interface utilisateur
  }

  /**
   * Renvoie les détails d'un pneu donné.
   *
   * @param pneu Pneu dont les détails doivent être récupérés.
   * @return Détails du pneu.
   */
  private String getDetailsPneu(Pneu pneu) {
    if (pneu != null) {
      return "Marque : " + pneu.getMarque() + ", Largeur : " + pneu.getLargeur();
    } else {
      return "Pneu non défini";
    }
  }


}