/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module BasketRolling {
    requires org.postgresql.jdbc;
    requires org.hibernate.orm.core;
    requires jakarta.transaction;
    requires jakarta.persistence;
    requires jakarta.cdi;
    requires password4j;
    requires javafx.controls;
    requires javafx.fxml;
       
    
    opens org.basketrolling to org.hibernate.orm.core, org.postgresql.jdbc;
    opens org.basketrolling.beans to org.hibernate.orm.core;
    opens org.basketrolling.gui.controller to javafx.fxml;
    opens org.basketrolling.gui.controller.hinzufuegen to javafx.fxml;
    opens org.basketrolling.gui.controller.bearbeiten to javafx.fxml;
    opens org.basketrolling.gui.menues to javafx.fxml, javafx.graphics;
    
     exports org.basketrolling;
     exports org.basketrolling.beans;
     exports org.basketrolling.dao;
     exports org.basketrolling.enums;
     exports org.basketrolling.gui.controller;
     exports org.basketrolling.gui.controller.hinzufuegen;
     exports org.basketrolling.gui.controller.bearbeiten;
     exports org.basketrolling.interfaces;
     exports org.basketrolling.service;
     exports org.basketrolling.utils;
   }
