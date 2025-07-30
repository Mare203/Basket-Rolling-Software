/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import org.basketrolling.beans.Login;
import org.basketrolling.enums.Rolle;

/**
 *
 * @author Marko
 */
public class Session {

    private static Login aktuellerBenutzer;

    public static void setBenutzer(Login benutzer) {
        aktuellerBenutzer = benutzer;
    }

    public static Login getBenutzer() {
        return aktuellerBenutzer;
    }

    public static boolean istAdmin() {
        return aktuellerBenutzer != null && aktuellerBenutzer.getRolle() == Rolle.ADMIN;
    }

    public static boolean istUser() {
        return aktuellerBenutzer != null && aktuellerBenutzer.getRolle() == Rolle.USER;
    }

    public static void logout() {
        aktuellerBenutzer = null;
    }
}
