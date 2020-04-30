package ru.makovey.financeCore.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * Типы операций.
 * id операции в enum соответствует id в БД
 */
public enum OperationType {
    INCOME(1), OUTCOME(2), TRANSFER(3), CONVERT(4);

    private OperationType(int id){
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    private static Map<Integer, OperationType> map = new HashMap<>();

    static {
        for(OperationType operation : OperationType.values()){
            map.put(operation.getId(), operation);
        }
    }

    public static OperationType getType(int id){
        return map.get(id);
    }
}
