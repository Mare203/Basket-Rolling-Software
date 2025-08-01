/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Die Klasse {@code BildUtil} stellt eine Hilfsmethode zur Verfügung, um Bilder
 * als {@link ImageView} mit fester Größe zu laden.
 *
 * Dies ist besonders nützlich für Symbole oder kleine Grafiken in
 * JavaFX-Oberflächen, z.B. in Buttons oder Tabellen.
 *
 * Die Bilddatei wird über den Ressourcenpfad geladen und auf eine feste Größe
 * (20x20 Pixel) skaliert.
 *
 * @author Marko
 */
public class BildUtil {

    /**
     * Lädt ein Bild aus dem angegebenen Ressourcenpfad und gibt es als
     * {@link ImageView} mit einer festen Größe von 20x20 Pixel zurück.
     *
     * @param pfad der Pfad zur Bildressource (relativ zum Klassenpfad)
     * @return ein {@link ImageView}-Objekt mit dem geladenen und skalierten
     * Bild
     */
    public static ImageView ladeBildSmall(String pfad) {
        Image icon = new Image(BildUtil.class.getResourceAsStream(pfad));
        ImageView bild = new ImageView(icon);
        bild.setFitHeight(20);
        bild.setFitWidth(20);
        return bild;
    }
}
