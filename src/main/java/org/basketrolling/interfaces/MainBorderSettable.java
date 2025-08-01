/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.basketrolling.interfaces;

import javafx.scene.layout.BorderPane;

/**
 * Dieses Interface ermöglicht es JavaFX-Controllern, Zugriff auf das zentrale
 * {@link BorderPane}-Layout der Hauptanwendung zu erhalten.
 *
 * Durch Implementierung dieser Schnittstelle kann der Controller beim Laden
 * durch eine Utility-Klasse (z. B. {@code MenuUtil}) mit dem Hauptlayout
 * verbunden werden. So ist das dynamische Nachladen von Views im Center-Bereich
 * der Anwendung möglich.
 *
 * Wird typischerweise verwendet in Kombination mit:
 * {@code ladeUndSetzeCenter(...)}.
 *
 * @author Marko
 */
public interface MainBorderSettable {

    /**
     * Setzt das Haupt-{@link BorderPane}, das dieser Controller verwenden soll.
     *
     * @param borderPane das zentrale BorderPane der Anwendung
     */
    void setMainBorder(BorderPane borderPane);
}
