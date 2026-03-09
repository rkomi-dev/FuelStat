package it.unipv.posw.Model.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DataDowloaderService {

	public void scaricaFile(String urlSorgente, String nomeFileDestinazione) {
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(urlSorgente);
            is = url.openStream();
            fos = new FileOutputStream(nomeFileDestinazione);

            byte[] buffer = new byte[8192]; // Buffer da 8KB per velocità
            int byteLetti;

            System.out.println("Download in corso da: " + urlSorgente);
            while ((byteLetti = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteLetti);
            }
            System.out.println("File salvato come: " + nomeFileDestinazione);

        } catch (IOException e) {
            System.err.println("Errore durante il download: " + e.getMessage());
        } finally {
            // Chiusura manuale rigorosa
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
