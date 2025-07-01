/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.basketrolling.interfaces;

import java.util.List;

/**
 *
 * @author Marko
 */
public interface ServiceInterface<T> {

    T create(T entity);

    T update(T entity);

    boolean delete(T entity);

    List<T> getAll();
}
