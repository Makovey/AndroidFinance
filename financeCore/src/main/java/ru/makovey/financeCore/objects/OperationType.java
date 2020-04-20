package ru.makovey.financeCore.objects;

import java.util.Objects;

public class OperationType {

    /**
     * Имя операции
     */
    private String name;

    /**
     * Идентификатор операции
     */
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Переопределены equals() & hashCode()
     * Для проверки уникальности
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationType that = (OperationType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
