package ru.makovey.financeCore.abstracts;

import java.util.Calendar;

import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Operation;

public abstract class AbstractOperation implements Operation {

    public AbstractOperation(OperationType operationType) {
        this.operationType = operationType;
    }

    public AbstractOperation(int id, Calendar date, String description, OperationType operationType) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.operationType = operationType;
    }

    public AbstractOperation(int id, OperationType operationType) {
        this.id = id;
        this.operationType = operationType;
    }

    /**
     * Id операции
     */
    private int id;

    /**
     * Дата совершения операции
     */
    private Calendar date;

    /**
     * Дополнительная информация
     */
    private String description;

    /**
     * Тип операции
     */
    private OperationType operationType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getDateTime(){
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public int compareTo(Operation operation) { // по умолчанию сортируем по дате
        return operation.getDateTime().compareTo(date);
    }
}
