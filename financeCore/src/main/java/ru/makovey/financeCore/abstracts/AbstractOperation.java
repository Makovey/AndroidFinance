package ru.makovey.financeCore.abstracts;

import java.util.Calendar;

public abstract class AbstractOperation {

    public AbstractOperation(){}

    public AbstractOperation(int id) {
        this.id = id;
    }

    public AbstractOperation(int id, Calendar date, String addInfo) {
        this.id = id;
        this.date = date;
        this.addInfo = addInfo;
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
    private String addInfo;

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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
