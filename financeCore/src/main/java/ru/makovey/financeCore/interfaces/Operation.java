package ru.makovey.financeCore.interfaces;

import java.util.Calendar;

import ru.makovey.financeCore.enums.OperationType;

public interface Operation extends Comparable<Operation> {

    int getId();

    void setId(int id);

    OperationType getOperationType();

    Calendar getDateTime();

    String getDescription();
}
