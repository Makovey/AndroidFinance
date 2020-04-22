package ru.makovey.financeCore.dao.interfaces;

/**
 * Интерфейс описывает общие действия по работе с БД для всех объектов
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
     * @param storage
     * @return
     */
    boolean update(T storage);

    /**
     * Удалить значение
     * @param storage
     * @return
     */
    boolean delete(T storage);
}
