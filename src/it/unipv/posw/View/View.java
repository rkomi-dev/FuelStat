package it.unipv.posw.View;

import java.util.List;
import java.util.Scanner;

import it.unipv.posw.Model.Distributore;

public class View {
	
	private Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void mostraMessaggio(String msg) {
        System.out.println(msg);
    }

    public String chiediInput(String domanda) {
        System.out.print(domanda + ": ");
        return scanner.nextLine().toUpperCase();
    }

    public void stampaClassifica(List<Distributore> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nessun distributore trovato con i criteri inseriti.");
            return;
        }
        System.out.println("\n--- TOP 10 PIU' ECONOMICI ---");
        System.out.printf("%-20s | %-15s | %-10s | %s\n", "BANDIERA", "COMUNE", "PREZZO", "GESTORE");
        System.out.println("---------------------------------------------------------------");
        for (Distributore d : lista) {
            System.out.printf("%-20s | %-15s | %6.3f € | %s\n", 
                d.getBandiera(), d.getComune(), d.getPrezzo(), d.getGestore());
        }
        System.out.println();
    }

}
