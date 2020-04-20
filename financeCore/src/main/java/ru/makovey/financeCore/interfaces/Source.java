package ru.makovey.financeCore.interfaces;

import ru.makovey.financeCore.objects.OperationType;

public interface Source {

    String getName();

    int getId();

    /**
     * Тип операции, взависимости от типа будут выбираться доступные деревья
     * @return тип операции
     */
    OperationType getOperationType();
}
