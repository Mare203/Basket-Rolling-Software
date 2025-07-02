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

- Login mit Berechtigungen (Admin: CRUD) / (User: Read-only)
- Spieler-verwaltung (Anlegen, Bearbeiten, Löschen von Spielern)
- Trainerverwaltung (Anlegen, Bearbeiten, Löschen von Trainern)
- Teamverwaltung (intern) (Anlegen, Bearbeiten, Löschen von Teams)
- Trainingsverwaltung (Anlegen, Bearbeiten, Löschen von Trainingsterminen)
- Statistikverwaltung von internen Spielern (Anlegen, Bearbeiten, Löschen von Statistiken)
- Hallenverwaltung (Anlegen, Bearbeiten, Löschen von Hallen)
- Ligenverwaltung, ohne Tabellensystem, nur zum zuordnen von Mannschaften (Anlegen, Bearbeiten, Löschen von Ligen)
- Spiele-verwaltung (Anlegen, Bearbeiten, Löschen von Spielen)

## Anforderungen

### Funktionale Anforderungen
#### Login
- Der User soll die Möglichkeit haben sich mit einem Benutzernamen + Passwort anzumelden
- Das System muss Benutzerrollen unterstützen und differenzierte Zugriffsrechte abhängig von der Rolle umsetzen
- Rollen:
    - Insgesamt soll es zwei Rollen geben: Admin & User
    - Admin: kann alle Daten verwalten (CRUD: Create, Read, Update, Delete)
    - User: kann Daten nur einsehen (Read-only)

#### Spielerverwaltung
- Spieler anlegen, bearbeiten, löschen
- Spieler müssen einer Mannschaft zugeordnet werden
- Zuweisung von Elternkontakte
- Erfassung persönlicher Daten: Größe, Geburtsdatum/Alter, E-Mail

#### Elternkontakte
- Pro Spieler können ein oder mehrere Elternkontakte gespeichert werden
- Name, Telefonnummer, E-Mail soll hinterlegt werden
- Das Feld 'Elternkontakt' darf bei Spielern ab der Herrenliga 2 leer bleiben, ist jedoch bei U8–U16 Spielern verpflichtend

#### Mannschaftsverwaltung
- Anlegen interner Mannschaften mit Liga, Training, Trainer
- Anlegen externer Mannschaften (z. B. Gegner)

#### Spieleverwaltung
- Neue Spiele mit Datum, Halle, Liga, internem und externem Team anlegen
- Punkte-Ergebnis speichern
- Zuordnung zur Liga
- Ein Spiel darf nicht doppelt angelegt werden (gleiches Datum + gleiche Teams)

#### Statistiken
- Pro Spiel können individuelle Statistiken pro Spieler erfasst werden:
    - Punkte
    - Fouls
    - Starter (ja/nein)

#### Training
- Zuordnung von Trainings zu Mannschaften
- Angabe: Wochentag, Dauer, Halle

#### Mitgliedsbeiträge
- Beiträgen Spielern pro Jahr zuordnen
- Angabe: Betrag, Datum der Zahlung

### Qualitätsanforderungen
Die Software soll neben der reinen Funktionalität auch bestimmte Qualitätsmerkmale erfüllen:
#### Benutzerfreundlichkeit
- Die Benutzeroberfläche (GUI) soll intuitiv und einfach bedienbar sein, auch für nicht-technische Benutzer (z. B. Trainer)
- Klare Menüführung und verständliche Eingabemasken sollen eine schnelle Orientierung ermöglichen
#### Zuverlässigkeit
- Die Anwendung soll stabil laufen und darf unter normalen Bedingungen nicht abstürzen
- Bei fehlerhaften Eingaben sollen Fehlermeldungen angezeigt werden
#### Performance
- Datenbankabfragen (z. B. Spielerlisten) sollen auch bei größeren Datenmengen effizient ausgeführt werden
#### Sicherheit
- Passwörter werden gehasht gespeichert
- Nur authentifizierte Benutzer haben Zugriff auf die Daten
- Rollenkonzept schützt vor unbefugten Änderungen (Admin & User)
#### Wartbarkeit & Erweiterbarkeit
- Der Code folgt dem Prinzip der 3-Schichten-Architektur (GUI, Service, DAO)
- Der Quellcode ist dokumentiert und so aufgebaut, dass Erweiterungen (z. B. neue Entitäten oder Features) mit geringem Aufwand möglich sind

## Benutzeroberfläche
Die Benutzeroberfläche der Verwaltungssoftware soll übersichtlich, intuitiv und rollenbasiert aufgebaut sein. Ziel ist es, sowohl Administratoren als auch Usern eine einfache Navigation und effiziente Bedienung zu ermöglichen.
Allgemeine Anforderungen:
- Klare Trennung zwischen Admin- und User-Ansicht
- Navigation über ein Menü oder Dashboard
Es soll ein zentrales Hauptmenü vorhanden sein, über das der Benutzer auf die Hauptfunktionen der Software zugreifen kann. Die Menüpunkte umfassen unter anderem:
- Spieler verwalten
- Trainer verwalten
- Interne/externe Teams verwalten
- Spiele verwalten
- Ligen verwalten
- Trainings verwalten
- Mitgliedsbeiträge verwalten
- Statistiken verwalten
Wählt man z.b. den Punkt „Spieler verwalten“, wird eine tabellarische Übersicht aller vorhandenen Spieler angezeigt. Innerhalb dieser Ansicht gibt es – je nach Benutzerrolle – unterschiedliche Möglichkeiten:
- Administratoren können Spieler bearbeiten, neue Spieler anlegen und bestehende löschen.
- User haben lediglich Leserechte und können die Spielerdaten einsehen, jedoch nicht bearbeiten oder neue anlegen.

Diese Struktur wiederholt sich sinngemäß für alle anderen Punkte wie Teams, Spiele usw., sodass Benutzer in jeder Übersicht Daten einsehen und – im Fall des Admins – auch verwalten können.
Die Bedienung soll konsistent aufgebaut sein, um Wiedererkennung und eine einfache Erlernbarkeit der Software zu gewährleisten


## Projektstruktur

```
src/
└── main/
    ├── java/
    │   └── org/
    │       └── basketrolling/
    │           ├── beans
    │           │   └── Elternkontakt.java
    │           │   └── Halle.java
    │           │   └── Liga.java
    │           │   └── Login.java
    │           │   └── MannschaftExtern.java
    │           │   └── MannschaftIntern.java
    │           │   └── Mitgliedsbeitrag.java
    │           │   └── Spiele.java
    │           │   └── Spieler.java
    │           │   └── Statistik.java
    │           │   └── Trainer.java
    │           │   └── Training.java
    │
    │           ├── dao
    │           │   └── BaseDAO.java
    │           │   └── ElternkontaktDAO.java
    │           │   └── HalleDAO.java
    │           │   └── LigaDAO.java
    │           │   └── LoginDAO.java
    │           │   └── MannschaftExternDAO.java
    │           │   └── MannschaftInternDAO.java
    │           │   └── MitgliedsbeitragDAO.java
    │           │   └── SpieleDAO.java
    │           │   └── SpielerDAO.java
    │           │   └── StatistikDAO.java
    │           │   └── TrainerDAO.java
    │           │   └── TrainingDAO.java
    |
    |           ├── enums
    │           |   └── Rolle.java 
    │
    │           ├── gui
    |
    │           ├── interfaces
    │           │   └── DAOInterface.java
    │           │   └── ServiceInterface.java
    |
    │           ├── service
    │
    │           ├── utils
    │           │   └── TryCatchUtil.java
    │   
    └── resources/
        ├── META-INF
        │   └── persistence.xml
