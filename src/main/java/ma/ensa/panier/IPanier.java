package ma.ensa.panier;

import java.util.Map;

import ma.ensa.entities.LigneCommande;
import ma.ensa.entities.Produit;

public interface IPanier {
	public void addItem(Produit produit, int quantite);
	public void remove(int idProduit);
	public Map<Integer, LigneCommande> getItems();
	public void setItems(Map<Integer, LigneCommande> items);

}
