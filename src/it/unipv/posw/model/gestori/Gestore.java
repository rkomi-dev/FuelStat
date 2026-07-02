package it.unipv.posw.model.gestori;

import it.unipv.posw.model.persistence.dao.DistributoreDAO;
import it.unipv.posw.model.service.DataDowloaderService;

public class Gestore {
	
	private static Gestore instance;
	private DistributoreDAO dao;
	private DataDowloaderService service;
	
	public static Gestore getInstance() {
		if (instance == null) {
			instance = new Gestore();
	    }
	    return instance;
	}
	
	private Gestore() {
		
		this.dao = new DistributoreDAO();
		this.service = new DataDowloaderService();
	}

	public DistributoreDAO getDao() {
		return dao;
	}

	public DataDowloaderService getService() {
		return service;
	}
	
	

}
