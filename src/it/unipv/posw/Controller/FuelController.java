package it.unipv.posw.Controller;

import it.unipv.posw.Model.Service.DataDowloaderService;
import it.unipv.posw.Model.Persistence.DAO.DistributoreDAO;
import it.unipv.posw.View.View;
import it.unipv.posw.Model.Distributore;
import java.util.List;

public class FuelController {
    private DistributoreDAO dao;
    private View view;
    private DataDowloaderService downloader;

    public FuelController(DistributoreDAO dao, View view) {
        this.dao = dao;
        this.view = view;
        this.downloader = new DataDowloaderService();
    }

    public void avvia() {
        view.mostraMessaggio("Benvenuto in FuelWise!");
        
        // 1. Fase di Aggiornamento
        view.mostraMessaggio("Aggiornamento dati in corso dal sito MIMIT...");
        downloader.scaricaFile("https://www.mimit.gov.it/images/exportCSV/anagrafica_impianti_attivi.csv", "anagrafica.csv");
        downloader.scaricaFile("https://www.mimit.gov.it/images/exportCSV/prezzo_alle_8.csv", "prezzi.csv");
        
        view.mostraMessaggio("Popolamento database...");
        dao.caricaAnagrafica("anagrafica.csv");
        dao.caricaPrezziNelDB("prezzi.csv");
        
        // 2. Ciclo di ricerca
        boolean continua = true;
        while (continua) {
            String prov = view.chiediInput("Inserisci la sigla della provincia (es. PV)");
            String carb = view.chiediInput("Tipo carburante (Benzina/Gasolio/Metano/GPL)");
            
            // Per ora forziamo '1' per Self per il test
            List<Distributore> risultati = dao.getTop10ByProvincia(prov, carb);
            
            view.stampaClassifica(risultati);
            
            String risp = view.chiediInput("Vuoi fare un'altra ricerca? (S/N)");
            if (risp.equalsIgnoreCase("N")) continua = false;
        }
        view.mostraMessaggio("Grazie per aver usato FuelWise. Arrivederci!");
    }
}