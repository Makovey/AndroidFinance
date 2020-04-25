package ru.makovey.financeCore.interfaces;

import ru.makovey.financeCore.enums.OperationTypeEnum;

public interface Source extends CompositeTree{

    /**
     * Тип операции, взависимости от типа будут выбираться доступные деревья
     * @return тип операции
     */
    OperationTypeEnum getOperationType();
}
