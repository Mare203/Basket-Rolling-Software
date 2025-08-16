/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.ansehen;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zur Anzeige von Spielerinformationen.
 * <p>
 * Diese Klasse stellt die Daten eines ausgewählten {@link Spieler} dar,
 * einschließlich Stammdaten (Name, Geburtsdatum, Größe, E-Mail), Zugehörigkeit
 * zu einer {@link MannschaftIntern}, Informationen zum {@link Mitgliedsbeitrag}
 * sowie statistische Werte wie Spiele und Punkte pro Spiel (PPG).
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren, welche für die Abfragen der zugehörigen Daten
 * benötigt werden.
 * </p>
 *
 * @author Marko
 */
public class SpielerAnzeigenController implements Initializable {

    Spieler bearbeitenSpieler;

    SpielerDAO spielerDao;
    SpielerService spielerService;

    MitgliedsbeitragZuweisungDAO zuweisungDao;
    MitgliedsbeitragZuweisungService zuweisungService;

    MannschaftInternDAO mannschaftDao;
    MannschaftInternService mannschaftService;

    MitgliedsbeitragDAO mitgliedsbeitragDAO;
    MitgliedsbeitragService mitgliedsbeitragService;

    StatistikDAO statistikDao;
    StatistikService statistikService;

    MitgliedsbeitragZuweisung zuweisung;

    @FXML
    private Label lbName;

    @FXML
    private Label lbGeburtsdatum;

    @FXML
    private Label lbGroesse;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbMannschaft;

    @FXML
    private Label lbMitgliedsbeitrag;

    @FXML
    private Label lbSpiele;

    @FXML
    private Label lbPPG;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt alle benötigten Service- und DAO-Instanzen für Spieler,
     * Mitgliedsbeiträge, Mannschaften und Statistiken.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spielerDao = new SpielerDAO();
        zuweisungDao = new MitgliedsbeitragZuweisungDAO();
        mannschaftDao = new MannschaftInternDAO();
        mitgliedsbeitragDAO = new MitgliedsbeitragDAO();
        statistikDao = new StatistikDAO();

        spielerService = new SpielerService(spielerDao);
        zuweisungService = new MitgliedsbeitragZuweisungService(zuweisungDao);
        mannschaftService = new MannschaftInternService(mannschaftDao);
        mitgliedsbeitragService = new MitgliedsbeitragService(mitgliedsbeitragDAO);
        statistikService = new StatistikService(statistikDao);
    }

    /**
     * Initialisiert die Anzeige mit den Daten des übergebenen Spielers.
     * <p>
     * Zeigt Stammdaten, Mannschaftszugehörigkeit, Mitgliedsbeitrag und
     * statistische Werte (Spiele, PPG) in den entsprechenden Labels an.
     * </p>
     *
     * @param spieler der anzuzeigende {@link Spieler}
     */
    public void initSpieler(Spieler spieler) {
        this.bearbeitenSpieler = spieler;
        List<MitgliedsbeitragZuweisung> vorhandeneZuweisung = zuweisungService.getBySpieler(spieler);

        if (!vorhandeneZuweisung.isEmpty()) {
            zuweisung = vorhandeneZuweisung.get(0);
            lbMitgliedsbeitrag.setText(zuweisung.isBezahlt() ? "Ja" : "Nein");
        } else {
            lbMitgliedsbeitrag.setText("Kein Mitgliedsbeitrag zugewiesen");
        }

        lbName.setText(spieler.getVorname() + " " + spieler.getNachname());
        lbGeburtsdatum.setText(String.valueOf(spieler.getGeburtsdatum()) + " (" + spieler.getAlter() + ")");
        lbGroesse.setText(String.format("%.2f m", spieler.getGroesse()));
        lbEmail.setText(spieler.geteMail());
        lbMannschaft.setText(spieler.getMannschaftIntern().getName());

        List<Statistik> statsitikList = statistikService.getBySpieler(spieler);

        if (!statsitikList.isEmpty()) {
            lbSpiele.setText(String.valueOf(statsitikList.size()));
        } else {
            lbSpiele.setText("Der Spieler hat noch kein Spiel bestritten");
        }
        Double ppg = statistikService.getPpgBySpieler(spieler);

        if (ppg != null) {
            lbPPG.setText(String.valueOf(ppg));
        } else {
            lbPPG.setText("Der Spieler hat noch kein Spiel bestritten");
        }
    }

    /**
     * Schließt die Anzeige des Spielers ohne Bestätigungsdialog.
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenOhneWarnung(lbName);
    }
}
