/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;

/**
 *
 * @author Marko
 */
public class StatistikController implements Initializable, MainBorderSettable {

    SpielerDAO spielerDao;
    MannschaftInternDAO mannInDao;
    SpieleDAO spieleDao;
    StatistikDAO statsitikDao;

    SpielerService spielerService;
    SpieleService spieleService;
    MannschaftInternService mannInService;
    StatistikService statsitikService;

    MannschaftIntern mannIn1;
    MannschaftIntern mannIn2;
    MannschaftIntern mannIn3;

    @FXML
    private BarChart<String, Integer> bcRolling1;

    @FXML
    private BarChart<String, Integer> bcRolling2;

    @FXML
    private BarChart<String, Integer> bcRolling3;

    @FXML
    private NumberAxis punkte1;

    @FXML
    private CategoryAxis gegner1;

    @FXML
    private Label lbAktiveSpieler1;

    @FXML
    private Label lbAktiveSpieler2;

    @FXML
    private Label lbAktiveSpieler3;

    @FXML
    private Label lbDurchschnittsalter1;

    @FXML
    private Label lbDurchschnittsalter2;

    @FXML
    private Label lbDurchschnittsalter3;

    @FXML
    private Label lbTopScorer1;

    @FXML
    private Label lbTopScorer2;

    @FXML
    private Label lbTopScorer3;

    @FXML
    private Label lbPunktedurchschnit1;

    @FXML
    private Label lbPunktedurchschnit2;

    @FXML
    private Label lbPunktedurchschnit3;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        spielerDao = new SpielerDAO();
        mannInDao = new MannschaftInternDAO();
        statsitikDao = new StatistikDAO();

        spieleService = new SpieleService(spieleDao);
        spielerService = new SpielerService(spielerDao);
        mannInService = new MannschaftInternService(mannInDao);
        statsitikService = new StatistikService(statsitikDao);

        List<MannschaftIntern> mannschaft1 = mannInService.getByName("Basket Rolling/1");
        List<MannschaftIntern> mannschaft2 = mannInService.getByName("Basket Rolling/2");
        List<MannschaftIntern> mannschaftRossau = mannInService.getByName("Basket Rolling Rossau");

        mannIn1 = mannschaft1.get(0);
        mannIn2 = mannschaft2.get(0);
        mannIn3 = mannschaftRossau.get(0);

        List<Spiele> spieleList1 = spieleService.getByMannschaft(mannIn1);
        List<Spiele> spieleList2 = spieleService.getByMannschaft(mannIn2);
        List<Spiele> spieleList3 = spieleService.getByMannschaft(mannIn3);

        bcRolling1.getData().add(setzeStatistik(spieleList1));
        bcRolling2.getData().add(setzeStatistik(spieleList2));
        bcRolling3.getData().add(setzeStatistik(spieleList3));

        List<Spieler> aktiveSpieler1 = spielerService.getByAktivUndMannschaft(true, mannIn1);
        List<Spieler> aktiveSpieler2 = spielerService.getByAktivUndMannschaft(true, mannIn2);
        List<Spieler> aktiveSpieler3 = spielerService.getByAktivUndMannschaft(true, mannIn3);

        lbAktiveSpieler1.setText("Aktive Spieler im Kader: " + aktiveSpieler1.size());
        lbAktiveSpieler2.setText("Aktive Spieler im Kader: " + aktiveSpieler2.size());
        lbAktiveSpieler3.setText("Aktive Spieler im Kader: " + aktiveSpieler3.size());

        lbDurchschnittsalter1.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler1)));
        lbDurchschnittsalter2.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler2)));
        lbDurchschnittsalter3.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler3)));

        Object[] topScorer1 = statsitikService.getTopScorerByMannschaft(mannIn1);
        Object[] topScorer2 = statsitikService.getTopScorerByMannschaft(mannIn2);
        Object[] topScorer3 = statsitikService.getTopScorerByMannschaft(mannIn3);

        zeigeTopScorer(lbTopScorer1, topScorer1);
        zeigeTopScorer(lbTopScorer2, topScorer2);
        zeigeTopScorer(lbTopScorer3, topScorer3);

        zeigePunkteschnitt(lbPunktedurchschnit1, mannIn1);
        zeigePunkteschnitt(lbPunktedurchschnit2, mannIn2);
        zeigePunkteschnitt(lbPunktedurchschnit3, mannIn3);
    }

    private XYChart.Series setzeStatistik(List<Spiele> spieleList) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Erzielte Punkte");

        for (Spiele spiel : spieleList) {
            String mannEx = spiel.getMannschaftExtern().getName() + "(" + spiel.getDatum().toString() + ")";
            int punkte = spiel.getInternPunkte();

            XYChart.Data<String, Integer> data = new XYChart.Data<>(mannEx, punkte);
            series.getData().add(data);

            data.nodeProperty().addListener((obs, altNode, neuNode) -> {
                if (neuNode != null) {
                    Label label = new Label(String.valueOf(punkte));

                    StackPane bar = (StackPane) neuNode;
                    bar.getChildren().add(label);

                }
            });
        }
        return series;
    }

    public void backToHauptmenue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/hauptmenu/hauptmenueCenter.fxml"));
            Parent hauptMenue = loader.load();

            mainBorderPane.setCenter(hauptMenue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double berechneDurchschnittsalter(List<Spieler> spielerListe) {
        LocalDate heute = LocalDate.now();
        int summe = 0;
        int zaehler = 0;

        for (Spieler spieler : spielerListe) {
            LocalDate geburt = spieler.getGeburtsdatum();
            if (geburt != null) {
                int alter = Period.between(geburt, heute).getYears();
                summe += alter;
                zaehler++;
            }
        }

        return zaehler == 0 ? 0 : (double) summe / zaehler;
    }

    private void zeigeTopScorer(Label label, Object[] scorer) {
        if (scorer != null) {
            Spieler spieler = (Spieler) scorer[0];
            double punkte = ((Number) scorer[1]).doubleValue();
            int spiele = ((Number) scorer[2]).intValue();

            label.setText("Top-Scorer: " + spieler.getVorname() + " " + spieler.getNachname()
                    + " | " + String.format("%.1f", punkte) + " PPG in "
                    + spiele + (spiele == 1 ? " Spiel" : " Spielen"));
        } else {
            label.setText("Top-Scorer: Nicht verfügbar");
        }
    }

    private void zeigePunkteschnitt(Label label, MannschaftIntern mannschaft) {
        Double schnitt = spieleService.getPunkteschnittProMannschaft(mannschaft);
        if (schnitt != null) {
            label.setText("Punktedurchschnitt: " + String.format("%.1f", schnitt) + " PPG");
        } else {
            label.setText("Punktedurchschnitt: Nicht verfügbar");
        }
    }
}
