/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Marko
 */
public class BildUtil {

    public static ImageView ladeBildSmall(String pfad) {
        Image icon = new Image(BildUtil.class.getResourceAsStream(pfad));
        ImageView bild = new ImageView(icon);
        bild.setFitHeight(20);
        bild.setFitWidth(20);
        return bild;
    }
}
