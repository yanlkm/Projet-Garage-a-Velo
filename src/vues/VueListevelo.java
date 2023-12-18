package vues;


import elements.Velo;
import facade.GarageVelo;
import ihm.Controleur;
import interfaces.Observer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import reponses.VerifierResultat;
import verifications.VerifierVelo;


/**
 * Classe représentant la vue listant les vélos.
 */
public class VueListevelo extends JFrame implements Observer {

  private static final long serialVersionUID = 1L;
  private JList<String> listeVelo;
  private DefaultListModel<String> listModel;
  private static GarageVelo garageV;
  
  @SuppressWarnings("unused")
  private JComboBox<String> contientChambreDropdown; // Menu déroulant pour le "contientChambre"
  
  /**
   * Constructeur de la vue listant les vélos.
   *
   * @param garage L'instance de GarageVelo à afficher.
   */
  public VueListevelo(GarageVelo garage) {
  
    VueListevelo.garageV = garage;
    VueListevelo.garageV.addObserver(this);

    listModel = new DefaultListModel<>();
    listeVelo = new JList<>(listModel);

    JScrollPane scrollPane = new JScrollPane(listeVelo);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    // Création d'un panneau pour les champs de configuration de l'usine
    JPanel configPanel = new JPanel();
    configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

    JTextField marqueVeloField = new JTextField(GarageVelo.recupUsine().getMarqueVelo());
    marqueVeloField.setMaximumSize(new Dimension(150, 20)); //Taille préférée pour le champ de texte
    configPanel.add(createFieldPanel("Marque Vélo: ", marqueVeloField));

    JTextField marqueBatterieField = new JTextField(GarageVelo.recupUsine().getMarqueBatterie());
    marqueBatterieField.setMaximumSize(new Dimension(150, 20)); //Taille pour le champ de texte
    configPanel.add(createFieldPanel("Marque Batterie: ", marqueBatterieField));

    JTextField marquePneuField = new JTextField(GarageVelo.recupUsine().getMarquePneu());
    marquePneuField.setMaximumSize(new Dimension(150, 20)); // Taille pour le champ de texte
    configPanel.add(createFieldPanel("Marque Pneu: ", marquePneuField));

    JTextField largeurPneuField = new JTextField(GarageVelo
         .recupUsine().getLargeurPneu().toString());
    largeurPneuField.setMaximumSize(new Dimension(150, 20)); // Taille  pour le champ de texte
    configPanel.add(createFieldPanel("Largeur Pneu: ", largeurPneuField));

    JTextField puissanceField = new JTextField(GarageVelo
         .recupUsine().getPuissanceBatterie().toString());
    puissanceField.setMaximumSize(new Dimension(150, 20)); // Taille pour le champ de texte
    configPanel.add(createFieldPanel("Puissance Batterie: ", puissanceField));


    
    // Définir la police avec une taille spécifique pour les champs JTextField
    Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16); // Exemple de taille de police 16
    marqueVeloField.setFont(fieldFont);
    marqueBatterieField.setFont(fieldFont);
    marquePneuField.setFont(fieldFont);
    largeurPneuField.setFont(fieldFont);
    puissanceField.setFont(fieldFont);
    
    String[] choixContientChambre = { "OUI", "NON" };
    JComboBox<String> contientChambreDropdown = new JComboBox<>(choixContientChambre);
    

    
    // Définir la largeur préférée pour la liste déroulante
    contientChambreDropdown.setMaximumSize(new Dimension(100, 
            contientChambreDropdown.getPreferredSize().height));

    configPanel.add(createFieldPanel("Contient Chambre: ", contientChambreDropdown));
    // Bouton pour configurer l'usine
    JButton configurerButton = new JButton("Configurer");
    configurerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String marqueVelo = marqueVeloField.getText();
            String marqueBatterie = marqueBatterieField.getText();
            String marquePneu = marquePneuField.getText();
            String largeurPneutext = largeurPneuField.getText();
            String puissancePneu = puissanceField.getText(); 

            boolean contientChambre = contientChambreDropdown.getSelectedItem().equals("OUI");
            boolean verifierInt = verifierBonTypeDonnees(largeurPneuField.getText())
                    && verifierBonTypeDonnees(puissanceField.getText());
            if (largeurPneutext.isEmpty() || puissancePneu.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Veuillez remplir correctement tous les champs.");
              return; 
            }
            if (verifierInt) {
            
              Integer largeurPneu = Integer.parseInt(largeurPneuField.getText());
              Integer puissance = Integer.parseInt(puissanceField.getText());
              // Vérifier si les champs sont remplis
              if (!marqueVelo.isEmpty() && !marqueBatterie.isEmpty() && !marquePneu.isEmpty() 
                  && !largeurPneutext.isEmpty() && verifierInt && !puissancePneu.isEmpty()
                  && largeurPneu > 20 && largeurPneu < 30) {
                // Appeler la méthode configurerUsine du GarageVelo
                VerifierResultat resultat = garage.configurerUsine(marqueVelo, marqueBatterie,
                      marquePneu, puissance, largeurPneu, contientChambre);
                if (resultat == VerifierResultat.USINE_MODIFIE) {
                  JOptionPane.showMessageDialog(null, "Usine configurée avec succès !");
                } else {
                  JOptionPane.showMessageDialog(null, "Impossible de configurer l'usine.");
                }
              } else {
                JOptionPane.showMessageDialog(null, "Veuillez remplir "
                    + "correctement tous les champs.");
            }
          } else {
              JOptionPane.showMessageDialog(null, "Veuillez remplir "
                   + "correctement tous les champs.");  
          }
        }
    });
    
    configPanel.add(configurerButton, BorderLayout.SOUTH);
    // Ajout du panneau de configuration à votre interface
    panel.add(configPanel, BorderLayout.WEST);

    JTextField modelVelo = new JTextField("");
    modelVelo.setMaximumSize(new Dimension(150, 20)); // Taille préférée pour le champ de texte
    configPanel.add(createFieldPanel("Model Vélo: ", modelVelo));

    JButton createButton = new JButton("Créer un vélo");
    createButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (modelVelo.getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "Impossible de créer le vélo sans modèle");
        } else {
          VerifierResultat resultat = garage.creerVelo(GarageVelo.recupUsine().getMarqueVelo(),
                modelVelo.getText());
          garage.notifyObservers();
          if (resultat != VerifierResultat.CONFORME) {
            JOptionPane.showMessageDialog(null, resultat);
          }
        }
      }
    });

    panel.add(createButton, BorderLayout.SOUTH);
    JButton createFromFileButton = new JButton("Créer à partir d'un fichier JSON");
    createFromFileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          String filePath = selectedFile.getAbsolutePath();
          VerifierResultat resultat = garage.creerVeloapartirfichier(filePath);
          if (resultat == VerifierResultat.VELO_RECUPERE) {
            System.out.println("Vélo créé avec succès !");
            garage.notifyObservers();
            JOptionPane.showMessageDialog(null, "Vélos ajoutés (les vélos avec les numéros de série"
                + "déjà existant ne sont pas ajoutés)");
          } else if (resultat == VerifierResultat.VELO_RECUPERE_AVEC_NON_CONFORMITE) {
            garage.notifyObservers();
            JOptionPane.showMessageDialog(null,
                "Des vélos ne sont pas conformes ou incorrects (les vélos avec les numéros de \n"
                    + " série existants ne sont pas ajoutés) ");
          } else {
            JOptionPane.showMessageDialog(null,
                "Impossible de créer le vélo à partir du fichier : " + resultat);
          }
        }
      }
    });
    panel.add(createFromFileButton, BorderLayout.NORTH);

    listeVelo.setCellRenderer(new CustomCellRenderer());

    listeVelo.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          int index = listeVelo.locationToIndex(e.getPoint());
          if (index != -1) {
            Rectangle bounds = listeVelo.getCellBounds(index, index);
            if (bounds != null && e.getX() > 100) {
              String selectedVelo = listModel.getElementAt(index);
              String[] parts = selectedVelo.split(": ");
              int numeroSerie = Integer.parseInt(parts[1]);
              Velo veloSelectionne = GarageVelo.recupListe().get(numeroSerie);
              if (veloSelectionne != null) {
                garage.selectionVelo(veloSelectionne);
                Controleur.afficherVelo(veloSelectionne);
                System.out.println("Unvelo a été selectionné");
              }
            } else {
              int clickedIndex = listeVelo.locationToIndex(e.getPoint());
              String selectedVelo = listModel.getElementAt(clickedIndex);
              String[] parts = selectedVelo.split(": ");
              int numeroSerie = Integer.parseInt(parts[1]);
              garageV.supprimerVelo(numeroSerie);
              garageV.notifyObservers();
              System.out.println(garageV.sizeObservers());
            }
          }
        }
      }
    });

    setTitle("Liste des vélos");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(panel);
    pack();
    setVisible(true);
  }


  /**
   * Méthode appelée lorsqu'une mise à jour est effectuée dans la liste des vélos.
   *
   * @param listeVelos Liste des vélos mise à jour.
   */
  @Override
  public void update(List<Velo> listeVelos) {
    SwingUtilities.invokeLater(() -> {
      listModel.clear();
      for (Velo velo : listeVelos) {
        listModel.addElement("   Vélo numéro : " + velo.getNumeroSerie());
      }
    });
  }

  /**
   * Crée un panneau de champs avec un label et un champ de texte.
   *
   * @param label Libellé du champ.
   * @param field Champ de texte à ajouter.
   * @return Le panneau de champs créé.
   */
  private JPanel createFieldPanel(String label, JComponent field) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.add(new JLabel(label));
    panel.add(field);
    return panel;
  }

  /**
   * Vérifie si la valeur de la puissance est un entier valide.
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
   * Rendu personnalisé pour chaque cellule de la liste de vélos.
   */
  private class CustomCellRenderer extends JPanel implements ListCellRenderer<String> {
    /**
     * Numéro de version pour la sérialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la classe CustomCellRenderer.
     */
    public CustomCellRenderer() {
      setLayout(new BorderLayout());
    }

    /**
     * Renvoie le composant de rendu pour une cellule de liste spécifiée.
     *
     * @param list         Liste à rendre.
     * @param value        Valeur à afficher.
     * @param index        Index de la cellule à rendre.
     * @param isSelected   Vrai si la cellule est sélectionnée, sinon faux.
     * @param cellHasFocus Vrai si la cellule a le focus, sinon faux.
     * @return Composant de rendu pour la cellule spécifiée.
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, 
        String value, int index, boolean isSelected, boolean cellHasFocus) {
      removeAll();

      JLabel label = new JLabel(value);
      add(label, BorderLayout.CENTER);

      JButton deleteButton = new JButton("Supprimer");
      // Obtenir la largeur du panneau parent
      @SuppressWarnings("unused")
      Dimension rendererSize = getSize();

      deleteButton.setPreferredSize(new Dimension(100, 30)); // Définir la taille du bouton

      deleteButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }
      });

      add(deleteButton, BorderLayout.WEST);
      label.setText(value);

      String[] parts = value.split(": ");
      int numeroSerie = Integer.parseInt(parts[1]);
      Velo veloSelectionne = GarageVelo.recupListe().get(numeroSerie);
      if (veloSelectionne != null) {
        if (VerifierVelo.estConforme(veloSelectionne) == VerifierResultat.CONFORME) {
          // Chargez l'icône verte (assurez-vous d'avoir l'icône dans les ressources)
          ImageIcon greenIcon = new ImageIcon(getClass().getResource("../images/green.png"));
          // Récupérez l'image de l'ImageIcon
          Image originalImage = greenIcon.getImage();
          // Redimensionnez l'image à la taille souhaitée
          Image scaledImage = originalImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

          // Créez une nouvelle ImageIcon à partir de l'image redimensionnée
          ImageIcon resizedIcon = new ImageIcon(scaledImage);
          label.setIcon(resizedIcon);
        } else {
          // Chargez l'icône rouge (assurez-vous d'avoir l'icône dans les ressources)
          ImageIcon redIcon = new ImageIcon(getClass().getResource("../images/red.png"));

          // Récupérez l'image de l'ImageIcon
          Image originalImage = redIcon.getImage();
          // Redimensionnez l'image à la taille souhaitée
          Image scaledImage = originalImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

          // Créez une nouvelle ImageIcon à partir de l'image redimensionnée
          ImageIcon resizedIcon = new ImageIcon(scaledImage);
          label.setIcon(resizedIcon);

        }
      }

      return this;
    }
  }


}
