package it.unipv.posw.Model.Persistence.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.posw.Model.Distributore;
import it.unipv.posw.Model.Persistence.DBConnection;
import it.unipv.posw.Model.Persistence.DAO.Interface.IDistributoreDAO;

public class DistributoreDAO implements IDistributoreDAO {
	
	private Connection c;

	@Override
	public List<Distributore> getTop10ByProvincia(String prov, String carburante) {
		
		List<Distributore> top10 = new ArrayList<>();
		PreparedStatement ps;
        String query = "SELECT i.id_impianto, i.gestore, i.bandiera, i.comune, i.provincia, " +
                     "p.prezzo, p.is_self, p.dt_comunicazione " +
                     "FROM impianti i " +
                     "JOIN prezzi p ON i.id_impianto = p.id_impianto " +
                     "WHERE i.provincia = ? AND p.tipo_carburante = ? " +
                     "AND p.dt_comunicazione > DATE_SUB(NOW(), INTERVAL 7 DAY)" +
                     "ORDER BY p.prezzo ASC LIMIT 10";

        try {
            
        	c = DBConnection.getInstance().startConnection();
        	ps = c.prepareStatement(query);
            ps.setString(1, prov.toUpperCase());
            ps.setString(2, carburante);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Distributore d = new Distributore(
                    rs.getInt("id_impianto"),
                    rs.getString("gestore"),
                    rs.getString("bandiera"),
                    rs.getString("provincia"),
                    rs.getDouble("prezzo"),
                    rs.getTimestamp("dt_comunicazione").toLocalDateTime()
                );
                
                d.setComune(rs.getString("comune"));
                d.setSelf(rs.getInt("is_self") == 1);
                
                top10.add(d);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	DBConnection.getInstance().closeConnection(c);
		}
        
        return top10;
    }

	@Override
	public void caricaPrezzi(String pathFile) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    BufferedReader br = null;
	    
	    // Mappa per filtrare i record: Chiave = "ID_TIPO" | Valore = Array di stringhe (la riga del CSV)
	    Map<String, String[]> mappaMigliori = new HashMap<>();

	    try {
	        br = new BufferedReader(new FileReader(pathFile));
	        br.readLine(); // Salta "Estrazione del..."
	        br.readLine(); // Salta intestazione

	        String riga;
	        while ((riga = br.readLine()) != null) {
	            String[] campi = riga.split("\\|");
	            if (campi.length < 5) continue;

	            String idImpianto = campi[0];
	            String tipoCarb = campi[1];
	            String dataCorrente = convertiData(campi[4]); // Formato yyyy-MM-dd HH:mm:ss
	            
	            // Creiamo una chiave univoca per l'impianto e il prodotto
	            String chiaveUnivoca = idImpianto + "_" + tipoCarb;

	            // Logica di filtraggio:
	            // Se la combinazione non esiste OPPURE la data di questa riga è più recente di quella salvata
	            if (!mappaMigliori.containsKey(chiaveUnivoca) || 
	                dataCorrente.compareTo(mappaMigliori.get(chiaveUnivoca)[4]) > 0) {
	                
	                // Aggiorniamo la data formattata nel campo per l'inserimento successivo
	                campi[4] = dataCorrente; 
	                mappaMigliori.put(chiaveUnivoca, campi);
	            }
	        }

	        // Ora che abbiamo solo i record più recenti nella Map, scriviamo nel DB
	        String sql = "INSERT INTO prezzi (id_impianto, tipo_carburante, prezzo, is_self, dt_comunicazione) VALUES (?, ?, ?, ?, ?)";
	        conn = DBConnection.getInstance().startConnection();
	        conn.setAutoCommit(false);
	        ps = conn.prepareStatement(sql);

	        int count = 0;
	        for (String[] dati : mappaMigliori.values()) {
	            ps.setInt(1, Integer.parseInt(dati[0]));
	            ps.setString(2, dati[1]);
	            ps.setDouble(3, Double.parseDouble(dati[2]));
	            ps.setInt(4, Integer.parseInt(dati[3]));
	            ps.setString(5, dati[4]); // Data già convertita nel ciclo precedente

	            ps.addBatch();
	            if (++count % 1000 == 0) ps.executeBatch();
	        }
	        
	        ps.executeBatch();
	        conn.commit();
	        System.out.println("Caricamento completato: " + count + " prezzi unici inseriti.");

	    } catch (Exception e) {
	        try { if (conn != null) conn.rollback(); } catch (SQLException se) { se.printStackTrace(); }
	        e.printStackTrace();
	    } finally {
	        chiudiRisorse(br, ps, conn);
	    }
	}
	
	@Override
	public void caricaAnagrafica(String pathFile) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        BufferedReader br = null;
        String sql = "INSERT INTO impianti (id_impianto, gestore, bandiera, comune, provincia) " +
                     "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE gestore=VALUES(gestore)";

        try {
            conn = DBConnection.getInstance().startConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            br = new BufferedReader(new FileReader(pathFile));

            br.readLine(); // Salta "Estrazione del..."
            br.readLine(); // Salta intestazione

            String riga;
            int count = 0;
            while ((riga = br.readLine()) != null) {
                String[] f = riga.split("\\|");
                if (f.length < 8) continue;

                pstmt.setInt(1, Integer.parseInt(f[0])); // idImpianto
                pstmt.setString(2, f[1]);               // Gestore
                pstmt.setString(3, f[2]);               // Bandiera
                pstmt.setString(4, f[6]);               // Comune
                pstmt.setString(5, f[7]);               // Provincia

                pstmt.addBatch();
                if (++count % 1000 == 0) pstmt.executeBatch();
            }
            pstmt.executeBatch();
            conn.commit();
            System.out.println("Anagrafica caricata!");
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException se) {}
            e.printStackTrace();
        } finally {
            chiudiRisorse(br, pstmt, conn);
        }
    }
	
	private String convertiData(String dataCsv) {
	    try {
	        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        java.util.Date date = inputFormat.parse(dataCsv);
	        return outputFormat.format(date);
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	private void chiudiRisorse(BufferedReader br, Statement st, Connection cn) {
        try {
            if (br != null) br.close();
            if (st != null) st.close();
            if (cn != null) DBConnection.getInstance().closeConnection(cn);
        } catch (Exception e) { e.printStackTrace(); }
    }
	
	public void resetDB() {
		
		Statement st;
		String query1 = "SET FOREIGN_KEY_CHECKS = 0";
		String query2 = "TRUNCATE TABLE prezzi";
		String query3 = "TRUNCATE TABLE impianti";
		String query4 = "SET FOREIGN_KEY_CHECKS = 1";
		
		try {
			c = DBConnection.getInstance().startConnection();
			st = c.createStatement();
			
			st.executeUpdate(query1);
			st.executeUpdate(query2);
			st.executeUpdate(query3);
			st.executeUpdate(query4);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.getInstance().closeConnection(c);
		}
	}
}


