package it.unipv.posw;

import it.unipv.posw.Controller.FuelController;
import it.unipv.posw.Model.Persistence.DAO.DistributoreDAO;
import it.unipv.posw.View.View;

public class Main {
	
	public static void main(String[] args) {
        DistributoreDAO dao = new DistributoreDAO();
        View view = new View();
        
        FuelController controller = new FuelController(dao, view);
        
        controller.avvia();
    }

}
