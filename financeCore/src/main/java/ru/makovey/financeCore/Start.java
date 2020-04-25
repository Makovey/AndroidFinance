package ru.makovey.financeCore;

import ru.makovey.financeCore.dao.impls.SourceDAOImpl;
import ru.makovey.financeCore.decorator.SourceSync;
import ru.makovey.financeCore.exceptions.CurrencyException;

public class Start {
    public static void main(String[] args) throws CurrencyException {
        SourceSync sourceSync = new SourceSync(new SourceDAOImpl());
        sourceSync.getAll();
    }
}
