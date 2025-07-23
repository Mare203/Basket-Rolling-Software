/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
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
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;

/**
 *
 * @author Marko
 */
public class StatistikController implements Initializable, MainBorderSettable {

    SpieleDAO spieleDao;
    SpieleService spieleService;

    MannschaftInternDAO mannInDao;
    MannschaftInternService mannInService;

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

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        mannInDao = new MannschaftInternDAO();

        spieleService = new SpieleService(spieleDao);
        mannInService = new MannschaftInternService(mannInDao);

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

}
