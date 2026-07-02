package it.unipv.posw.controller;

import it.unipv.posw.model.entities.Distributore;
import it.unipv.posw.model.gestori.Gestore;
import it.unipv.posw.view.View;

import java.io.File;
import java.util.List;

public class FuelController {
	
    private Gestore model;
    private View view;


    public FuelController(Gestore model, View view) {
        this.model = model;
        this.view = view;

    }

    public void avvia() {
        view.mostraMessaggio("Benvenuto in FuelStat!");

        File filePrezzi = new File("prezzi.csv");
        boolean deveAggiornare = false;

        if (!filePrezzi.exists()) {
            view.mostraMessaggio("Primo avvio: download dati necessario.");
            deveAggiornare = true;
        } else {
            long ultimaModifica = filePrezzi.lastModified();
            long oraCorrente = System.currentTimeMillis();
            long ventiquattroOre = 24 * 60 * 60 * 1000;

            if ((oraCorrente - ultimaModifica) > ventiquattroOre) {
                String scelta = view.chiediInput("I dati hanno più di 24h. Vuoi scaricare gli aggiornamenti? (S/N)");
                if (scelta.equalsIgnoreCase("S")) {
                    deveAggiornare = true;
                }
            } else {
                view.mostraMessaggio("Dati locali aggiornati (meno di 24h fa). Salto il download.");
            }
        }

       
        if (deveAggiornare) {
            eseguiAggiornamentoTotale();
        }

        // 2. Ciclo di ricerca
        boolean continua = true;
        while (continua) {
            String prov = view.chiediInput("\nInserisci la sigla della provincia (es. PV)");
            String carb = view.chiediInput("Tipo carburante (Benzina/Gasolio/Metano/GPL)");
            
            // Normalizzazione minima dell'input per evitare errori di battitura
            if(carb.equalsIgnoreCase("diesel")) carb = "Gasolio";

            List<Distributore> risultati = model.getDao().getTop10ByProvincia(prov, carb);
            view.stampaClassifica(risultati);
            
            String risp = view.chiediInput("Vuoi fare un'altra ricerca? (S/N)");
            if (risp.equalsIgnoreCase("N")) continua = false;
        }
        view.mostraMessaggio("Grazie per aver usato FuelStat. Arrivederci!");
    }

    /**
     * Metodo privato per gestire l'intero flusso di download e importazione
     */
    private void eseguiAggiornamentoTotale() {
        view.mostraMessaggio("Download in corso dal sito MIMIT...");
        model.getService().scaricaFile("https://www.mimit.gov.it/images/exportCSV/anagrafica_impianti_attivi.csv", "anagrafica.csv");
        model.getService().scaricaFile("https://www.mimit.gov.it/images/exportCSV/prezzo_alle_8.csv", "prezzi.csv");
        
        model.getDao().resetDB();
        
        view.mostraMessaggio("Popolamento database (operazione lunga, attendere)...");
        model.getDao().caricaAnagrafica("anagrafica.csv");
        model.getDao().caricaPrezzi("prezzi.csv");
    }
}