package it.unipv.posw;

import it.unipv.posw.controller.FuelController;
import it.unipv.posw.model.gestori.Gestore;
import it.unipv.posw.view.View;

public class Main {
	
	public static void main(String[] args) {

        View view = new View();
        
        FuelController controller = new FuelController(Gestore.getInstance(), view);
        
        controller.avvia();
    }

}
