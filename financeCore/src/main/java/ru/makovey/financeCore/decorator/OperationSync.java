package ru.makovey.financeCore.decorator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.makovey.financeCore.dao.interfaces.OperationDAO;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Operation;

public class OperationSync implements OperationDAO {

    private OperationDAO operationDAO;

    private List<Operation> operationList;
    private Map<OperationType, List<Operation>> operationMap = new EnumMap<>(OperationType.class);
    private Map<Integer, Operation> identityMap = new HashMap<>(); // список коллекций, нужна для быстрого получения объекта по id и не обращаться к БД

    private StorageSync storageSync;
    private SourceSync sourceSync;

    public OperationSync(OperationDAO operationDAO, StorageSync storageSync, SourceSync sourceSync) {
        this.operationDAO = operationDAO;
        this.storageSync = storageSync;
        this.sourceSync = sourceSync;
        initCollection();
    }

    private void initCollection() {
        operationList = operationDAO.getAll();

        for (Operation operation : operationList) {
            identityMap.put(operation.getId(), operation);
        }

        fillOperationMap(); // разделение по типам операции
    }

    private void fillOperationMap() {
        for (OperationType type : OperationType.values()) {
            operationMap.put(type, operationList.stream().filter(o -> o.getOperationType() == type).collect(Collectors.toList()));
        }
    }


    @Override
    public List<Operation> getList(OperationType operationType) {
        return operationMap.get(operationType);
    }

    @Override
    public List<Operation> getAll() {
        return operationList;
    }

    @Override
    public Operation get(int id) {
        return identityMap.get(id);
    }

    @Override
    public boolean update(Operation object) {
        if (operationDAO.update(object)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Operation object) {
        if (operationDAO.delete(object)) {//revertBalance
            //removeFromCollection
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Operation object) {
        return false;
    }
}
