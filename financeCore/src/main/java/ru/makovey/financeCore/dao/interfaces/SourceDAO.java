package ru.makovey.financeCore.dao.interfaces;

import java.util.List;

import ru.makovey.financeCore.enums.OperationTypeEnum;
import ru.makovey.financeCore.interfaces.Source;

public interface SourceDAO extends CommonDAO<Source> {

    List<Source> getList(OperationTypeEnum operationTypeEnum);
}
