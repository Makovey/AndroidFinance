package ru.makovey.financeCore.test;

import ru.makovey.financeCore.dao.impls.OperationDAOImpl;
import ru.makovey.financeCore.dao.impls.SourceDAOImpl;
import ru.makovey.financeCore.dao.impls.StorageDAOImpl;
import ru.makovey.financeCore.decorator.OperationSync;
import ru.makovey.financeCore.decorator.SourceSync;
import ru.makovey.financeCore.decorator.StorageSync;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.impls.DefaultSource;

public class Start {

    public static void main(String[] args) throws CurrencyException {

        StorageSync storageSync = new StorageSync(new StorageDAOImpl());
        SourceSync sourceSync = new SourceSync(new SourceDAOImpl());
        OperationSync operationSync = new OperationSync(new OperationDAOImpl(sourceSync.getIdentityMap(), storageSync.getIdentityMap()), storageSync, sourceSync);

        DefaultSource defaultSource = new DefaultSource("TEST");
        defaultSource.setOperationType(OperationType.OUTCOME);
        sourceSync.add(defaultSource);
        System.out.println("sourceSync.getAll() = " + sourceSync.getAll());
    }
}
