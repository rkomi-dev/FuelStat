package it.unipv.posw.Model.Persistence.DAO.Interface;

import java.util.List;

import it.unipv.posw.Model.Distributore;

public interface IDistributoreDAO {
	
	List<Distributore> getTop10ByProvincia(String prov, String carburante);
	void caricaPrezzi(String pathFile);
	void caricaAnagrafica(String pathFile);

}
