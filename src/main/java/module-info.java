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
    
    exports org.basketrolling;
    
    opens org.basketrolling to org.hibernate.orm.core, org.postgresql.jdbc;
   }