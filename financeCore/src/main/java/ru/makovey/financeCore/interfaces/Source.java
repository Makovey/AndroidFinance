package ru.makovey.financeCore.interfaces;

import ru.makovey.financeCore.enums.OperationType;

public interface Source extends CompositeTree{

    /**
     * Тип операции, взависимости от типа будут выбираться доступные деревья
     * @return тип операции
     */
    OperationType getOperationType();
}
