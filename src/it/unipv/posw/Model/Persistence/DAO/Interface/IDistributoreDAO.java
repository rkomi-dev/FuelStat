package it.unipv.posw.Model.Persistence.DAO.Interface;

import java.util.List;

import it.unipv.posw.Model.Distributore;

public interface IDistributoreDAO {
	
	List<Distributore> getTop10ByProvincia(String prov, String carburante);
	void caricaPrezziNelDB(String pathFile);
	void caricaAnagrafica(String pathFile);

}
