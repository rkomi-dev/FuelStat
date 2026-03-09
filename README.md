# FuelStat

<p float="left">
  <img alt="Static Badge" src="https://img.shields.io/badge/Backend-Java%20SE%2025-blueviolet?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp; 
  <img alt="Static Badge" src="https://img.shields.io/badge/Database-MySQL%208.4.8-lightblue?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp;
  <img alt="Static Badge" src="https://img.shields.io/badge/IDE-Eclipse%20-yellow?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
</p>

## Overview del Software

FuelWise è un'applicazione Java progettata per il monitoraggio e l'analisi in tempo reale dei prezzi dei carburanti sul territorio italiano. Il sistema automatizza l'intero ciclo di vita del dato: dal recupero delle informazioni ufficiali fornite dal MIMIT (Ministero delle Imprese e del Made in Italy) alla loro elaborazione e persistenza su database relazionale.

L'obiettivo principale è fornire all'utente uno strumento da riga di comando (CLI) rapido ed efficiente per individuare i 10 distributori più economici in una specifica provincia, filtrando per tipologia di carburante

## Requisiti per l'installazione del software

Per far eseguire il sotware in locale bisogna disporre di:

* **JDK 25**
* **MYSQL version 8.4.8**
* **JDBC: mysql-connector-j-9.5.0**

## Istruzioni di installazione

* **Clonare la repository**
```bash
https://github.com/rkomi-dev/FuelStat.git
```
* **Creare il database**
1. Spostati nella cartella clonata
```bash
cd FuelStat
```
2.  Esegui lo script sul terminale
```bash
mysql -u root -p < FuelStat/Resources/script_sql/FuelStat.sql
```
3. Nel file properties cambia db.username e db.password con le tue credenziali MYSQL

4. Avvio da terminale
```bash
chmod +x start.sh
```
```bash
./start.sh
```
