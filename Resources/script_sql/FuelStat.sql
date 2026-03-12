-- 1. Creazione del Database
CREATE DATABASE IF NOT EXISTS FuelStat;
USE FuelStat;


SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS prezzi;
DROP TABLE IF EXISTS impianti;
SET FOREIGN_KEY_CHECKS = 1;

-- 2. Tabella Impianti (Anagrafica)

CREATE TABLE IF NOT EXISTS impianti (
    id_impianto INT PRIMARY KEY,
    gestore VARCHAR(255),           
    bandiera VARCHAR(150),          
    tipo_impianto VARCHAR(100),
    nome_impianto VARCHAR(255),       
    indirizzo VARCHAR(255),
    comune VARCHAR(100),
    provincia CHAR(2),              
    latitudine DECIMAL(10, 8),
    longitudine DECIMAL(11, 8)
);

-- 3. Tabella Prezzi (Dati Variabili)

CREATE TABLE IF NOT EXISTS prezzi (
    id_prezzo INT AUTO_INCREMENT PRIMARY KEY,
    id_impianto INT,
    tipo_carburante VARCHAR(50),   
    prezzo DECIMAL(10, 3),           
    is_self TINYINT(1),            
    dt_comunicazione DATETIME,      
    FOREIGN KEY (id_impianto) REFERENCES impianti(id_impianto) ON DELETE CASCADE
);

-- 4. Ottimizzazione delle Prestazioni (Indici)

CREATE INDEX idx_provincia ON impianti(provincia);
CREATE INDEX idx_comune ON impianti(comune);
CREATE INDEX idx_carburante_data_prezzo ON prezzi(tipo_carburante, dt_comunicazione, prezzo);
