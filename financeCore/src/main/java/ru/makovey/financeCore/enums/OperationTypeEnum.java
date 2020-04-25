package ru.makovey.financeCore.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * Типы операций.
 * id операции в enum соответствует id в БД
 */
public enum OperationTypeEnum {
    INCOME(1), OUTCOME(2), TRANSFER(3), CONVERT(4);

    private OperationTypeEnum(int id){
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    private static Map<Integer, OperationTypeEnum> map = new HashMap<>();

    static {
        for(OperationTypeEnum operation : OperationTypeEnum.values()){
            map.put(operation.getId(), operation);
        }
    }

    public static OperationTypeEnum getType(int id){
        return map.get(id);
    }
}
