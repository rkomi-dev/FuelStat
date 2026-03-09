-- 1. Creazione del Database
CREATE DATABASE IF NOT EXISTS FuelStat;
USE FuelStat;

-- 2. Tabella Impianti (Anagrafica)
-- Contiene le informazioni fisse dei distributori.
CREATE TABLE IF NOT EXISTS impianti (
    id_impianto INT PRIMARY KEY,
    gestore VARCHAR(100),
    bandiera VARCHAR(50),
    tipo_impianto VARCHAR(50),
    nome_impianto VARCHAR(100),
    indirizzo VARCHAR(255),
    comune VARCHAR(100),
    provincia CHAR(2), -- Usiamo CHAR(2) perché le province sono sempre di 2 lettere (es. MI, RM)
    latitudine DECIMAL(10, 8),
    longitudine DECIMAL(11, 8)
);

-- 3. Tabella Prezzi (Dati Variabili)
-- Contiene i prezzi che aggiornerai quotidianamente.
CREATE TABLE IF NOT EXISTS prezzi (
    id_prezzo INT AUTO_INCREMENT PRIMARY KEY,
    id_impianto INT,
    tipo_carburante VARCHAR(50),
    prezzo DECIMAL(5, 3), -- Formato X.XXX (es. 1.789)
    is_self TINYINT(1),   -- 1 per Self, 0 per Servito
    dt_comunicazione DATETIME,
    FOREIGN KEY (id_impianto) REFERENCES impianti(id_impianto) ON DELETE CASCADE
);

-- 4. OTTIMIZZAZIONE: Creazione Indici
-- Questi sono fondamentali per far sì che la ricerca dei "Top 10 in provincia" sia istantanea
-- anche se hai 20.000 distributori nel database.
CREATE INDEX idx_provincia ON impianti(provincia);
CREATE INDEX idx_carburante_prezzo ON prezzi(tipo_carburante, prezzo);
