package it.unipv.posw;

import it.unipv.posw.controller.FuelController;
import it.unipv.posw.model.persistence.dao.DistributoreDAO;
import it.unipv.posw.view.View;

public class Main {
	
	public static void main(String[] args) {
        DistributoreDAO dao = new DistributoreDAO();
        View view = new View();
        
        FuelController controller = new FuelController(dao, view);
        
        controller.avvia();
    }

}
