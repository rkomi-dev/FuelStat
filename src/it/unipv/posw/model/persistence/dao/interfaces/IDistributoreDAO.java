package it.unipv.posw.model.persistence.dao.interfaces;

import java.util.List;

import it.unipv.posw.model.entities.Distributore;

public interface IDistributoreDAO {
	
	List<Distributore> getTop10ByProvincia(String prov, String carburante);
	void caricaPrezzi(String pathFile);
	void caricaAnagrafica(String pathFile);

}
