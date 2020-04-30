package ru.makovey.financeCore.dao.interfaces;

import java.util.List;

import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Operation;

public interface OperationDAO extends CommonDAO<Operation> {

    List<Operation> getList(OperationType operationType);

}
