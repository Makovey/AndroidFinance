package ru.makovey.financeCore;

import java.util.Currency;

import ru.makovey.financeCore.dao.impls.StorageDAOImpl;
import ru.makovey.financeCore.decorator.StorageSync;
import ru.makovey.financeCore.exceptions.CurrencyException;

public class Start {
    public static void main(String[] args) throws CurrencyException {
        StorageSync storageSync = new StorageSync(new StorageDAOImpl());

        storageSync.addCurrency(storageSync.get(10), Currency.getInstance("USD"));
    }
}
