package ru.makovey.financeCore.interfaces;

import java.util.List;

public interface CompositeTree {

    /**
     * Уникальный идентификатор узла
     *
     * @return id
     */
    int getId();

    /**
     * Устанавливает id
     */
    void setId(int id);


    /**
     * Имя узла
     *
     * @return name
     */
    String getName();


    /**
     * Добавить в дерево один дочерний элемент
     *
     * @param child
     */
    void addChild(CompositeTree child);


    /**
     * Удалить один дочерний элемент из дерева
     *
     * @param child
     */
    void removeChild(CompositeTree child);


    /**
     * Возвращает список дочерних элементов уровнем ниже
     *
     * @return список элементов
     */
    List<CompositeTree> getChilds();

    /**
     * Возвращает id родителя
     *
     * @return id
     */
    int getParentId();


    /**
     * Получить элемент по id
     *
     * @param id
     * @return элемент
     */
    CompositeTree getChildById(int id);


    /**
     * Получить родительский элемент
     *
     * @return родительский элемент
     */
    CompositeTree getParent();


    /**
     * Установка родительского элемента
     *
     * @param parent
     */
    void setParent(CompositeTree parent);


    /**
     * Есть ли дочерние элементы
     * @return
     */
    boolean hasChilds();

    /**
     * Есть ли родительский элемент
     * @return
     */
    boolean hasParent();
}
