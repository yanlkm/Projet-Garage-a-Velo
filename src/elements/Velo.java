package elements;

import interfaces.Element;
import interfaces.VisiteurElement;

/**
 * Représente l'objet Velo.
 */
public class Velo extends Marque implements Element {
  
  /**
  * Les attributs qui caractérise un velo par son numero de serie, sa marque et son model.
  */
  public Integer numeroSerie;
  private String model; 
  private Pneu[] pneus;
  private Batterie batterie;

  /**
  * Constructeur d'un velo.
  */  
  public Velo(Integer numeroSerie, String model, String marque, 
      Pneu pneuAvant, Pneu pneuArriere, Batterie batterie) {
    super(marque); 
    this.pneus = new Pneu[2]; 
    this.pneus[0] = pneuAvant;
    this.pneus[1] = pneuArriere;
    this.batterie = batterie; 
    this.model = model; 
    this.numeroSerie = numeroSerie; 
  }

  public Integer getNumeroSerie() {
    return this.numeroSerie;
  }

  public void setNumeroSerie(Integer numeroSerie) {
    this.numeroSerie = numeroSerie;
  }

  @Override
  public String getMarque() {
    return this.marque;
  }

  public void setMarque(String marque) {
    this.marque = marque;
  }

  public String getModel() {
    return this.model;
  }

  public void setModel(String model) {
    this.model = model;
  }
  
  public Batterie getBatterie() {
    return this.batterie;
  }

  public void setBatterie(Batterie batterie) {
    this.batterie = batterie; 
  }
  
  public Pneu[] getPneus() {
    return this.pneus;
  }
  
  public Pneu getPneuAvant() {
    return this.pneus[0];  
  }
  
  public Pneu getPneuArriere() {
    return this.pneus[1]; 
  }

  public void setPneus(Pneu pneuAvant, Pneu pneuArriere) {
    this.pneus[0] = pneuAvant;
    this.pneus[1] = pneuArriere;
  }

  @Override
  public void applique(VisiteurElement unVisiteur) {
    // TODO Auto-generated method stub
    unVisiteur.agitSur(this);
  }  
 
}

