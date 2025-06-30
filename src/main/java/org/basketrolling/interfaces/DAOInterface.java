/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.basketrolling.interfaces;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Marko
 */
public interface DAOInterface<T> {
    T save(T entity);
    T update (T entity);
    void delete (T entity);
    T findById (UUID id);
    List<T> findAll();
    
}
