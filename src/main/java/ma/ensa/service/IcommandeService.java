package ma.ensa.service;

import org.springframework.aop.framework.ProxyFactoryBean;

import ma.ensa.entities.Client;
import ma.ensa.entities.Commande;
import ma.ensa.panier.IPanier;
import ma.ensa.panier.Panier;

public interface IcommandeService {
	public Commande findById(int idEntity);
	public double getTotalPrix(int idCommande);
	void saveCommande(IPanier panier, Client c);
	

}
