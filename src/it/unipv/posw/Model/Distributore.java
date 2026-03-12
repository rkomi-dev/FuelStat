package it.unipv.posw.Model;

import java.time.LocalDateTime;

public class Distributore {
	
	    private int idImpianto;
	    private String gestore;    
	    private String bandiera;   
	    private String indirizzo;
	    private String comune;
	    private String provincia;  
	    private String tipoCarburante; 
	    private double prezzo;        
	    private boolean isSelf;      
	    private LocalDateTime dt_comunicazione;
		/**
		 * @param idImpianto
		 * @param gestore
		 * @param bandiera
		 * @param indirizzo
		 * @param comune
		 * @param provincia
		 * @param tipoCarburante
		 * @param prezzo
		 * @param dt_comunicazione 
		 * @param isSelf
		 */
		public Distributore(int idImpianto, String gestore, String bandiera, String provincia, double prezzo, LocalDateTime dt_comunicazione) {
			super();
			this.idImpianto = idImpianto;
			this.gestore = gestore;
			this.bandiera = bandiera;
			this.provincia = provincia;
			this.prezzo = prezzo;
			this.dt_comunicazione = dt_comunicazione;
		}
		
		public int getIdImpianto() {
			return idImpianto;
		}
		public void setIdImpianto(int idImpianto) {
			this.idImpianto = idImpianto;
		}
		public String getGestore() {
			return gestore;
		}
		public void setGestore(String gestore) {
			this.gestore = gestore;
		}
		public String getBandiera() {
			return bandiera;
		}
		public void setBandiera(String bandiera) {
			this.bandiera = bandiera;
		}
		public String getIndirizzo() {
			return indirizzo;
		}
		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}
		public String getComune() {
			return comune;
		}
		public void setComune(String comune) {
			this.comune = comune;
		}
		public String getProvincia() {
			return provincia;
		}
		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}
		public String getTipoCarburante() {
			return tipoCarburante;
		}
		public void setTipoCarburante(String tipoCarburante) {
			this.tipoCarburante = tipoCarburante;
		}
		public double getPrezzo() {
			return prezzo;
		}
		public void setPrezzo(double prezzo) {
			this.prezzo = prezzo;
		}
		public boolean isSelf() {
			return isSelf;
		}
		public void setSelf(boolean isSelf) {
			this.isSelf = isSelf;
		}

		public LocalDateTime getDt_comunicazione() {
			return dt_comunicazione;
		}

		public void setDt_comunicazione(LocalDateTime dt_comunicazione) {
			this.dt_comunicazione = dt_comunicazione;
		}
		
		
	    
	    
	}


