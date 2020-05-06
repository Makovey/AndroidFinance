package ru.makovey.financeCore.dao.interfaces;

/**
 * интерфейс описывает общие действия по работе с БД для всех объектов
 */

import java.util.List;

public interface CommonDAO<T> {

    /**
     * Получить список всех объектов
     * @return
     */
    List<T> getAll();

    /**
     * Получить значение объекта по id
     * @param id
     * @return
     */
    T get(int id);

    /**
     * Обновить значение
     * @param object
     * @return
     */
    boolean update(T object);

    /**
     * Удалить значение
     * @param object
     * @return
     */
    boolean delete(T object);

    /**
     * Добавить значение
     * @param object
     * @return
     */
    boolean add(T object);

}
