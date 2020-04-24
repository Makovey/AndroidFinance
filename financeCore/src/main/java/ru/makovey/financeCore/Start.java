package ru.makovey.financeCore;

import java.util.Currency;

import ru.makovey.financeCore.dao.impls.StorageDAOImpl;
import ru.makovey.financeCore.decorator.StorageSynchronizer;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.impls.DefaultStorage;

public class Start {
    public static void main(String[] args) throws CurrencyException {
        StorageSynchronizer storageSynchronizer = new StorageSynchronizer(new StorageDAOImpl());
        DefaultStorage tmpStorage = (DefaultStorage) storageSynchronizer.getAll().get(1).getChilds().get(0);

        storageSynchronizer.addCurrency(tmpStorage, Currency.getInstance("USD"));
        System.out.println("storageSynchronizer.getAll = " + storageSynchronizer.getAll());
    }
}
