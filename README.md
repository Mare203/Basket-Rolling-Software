# Basket-Rolling-Software
Eine Java-basierte Verwaltungssoftware für den Basketballverein "Basket Rolling".

## Projektbeschreibung

Dieses Projekt implementiert ein umfassendes Verwaltungssystem für den Basketballverein "Basket Rolling". Die Software ermöglicht die Verwaltung von Spielern, Teams intern, Spielen, Hallen, Trainingsterminen, Mitgliedsbeiträgen und Spielerstatistiken.

## Technologie-Stack

- Java 21
- PostgreSQL-Datenbank
- JDBC für Datenbankzugriff
- Maven für Dependency Management

Projektaufbau

Das Projekt folgt einer klassischen 3-Schichten-Architektur:

1. **Datenzugriffsschicht**: Verbindung zur Datenbank, Repositories
2. **Geschäftslogikschicht**: Services und Business-Logik
3. **Präsentationsschicht**: GUI

Funktionen

- Login mit Berechtigungen (Admin-Rechte - Anlegen, Bearbeiten, Löschen) / (Trainer-Rechte - Lesen von Daten)
- Spielerverwaltung (Anlegen, Bearbeiten, Löschen von Spielern)
- Teamverwaltung (intern) (Anlegen, Bearbeiten, Löschen von Teams)
- Trainerverwaltung (Anlegen, Bearbeiten, Löschen von Trainern)
- Trainingsverwaltung (Anlegen, Bearbeiten, Löschen von Trainingsterminen)
- Statistikverwaltung von internen Spielern (Anlegen, Bearbeiten, Löschen von Statistiken)
