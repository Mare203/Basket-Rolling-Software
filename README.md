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
    │           │   └── LoginDAO.java
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
    │           └── utils
    │
    │           
    │
    └── resources/
        ├── META-INF
        │   └── persistence.xml
