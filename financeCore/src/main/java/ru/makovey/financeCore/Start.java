package ru.makovey.financeCore;

import ru.makovey.financeCore.dao.impls.SourceDAOImpl;
import ru.makovey.financeCore.decorator.SourceSync;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Source;

public class Start {
    public static void main(String[] args) throws CurrencyException {
        SourceSync sourceSync = new SourceSync(new SourceDAOImpl());
        System.out.println(sourceSync.getAll());

        Source s = sourceSync.get(1);
        sourceSync.delete(s);

        Source s2 = sourceSync.get(2);
        sourceSync.delete(s2);


    }
}
