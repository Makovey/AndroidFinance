package ru.makovey.financeCore.impls.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Операция "Расход"
 */
public class OutcomeOperation extends AbstractOperation {
    public OutcomeOperation(Storage fromStorage, Source toSource, BigDecimal amount, java.util.Currency currency) {
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.amount = amount;
        Currency = currency;
    }

    public OutcomeOperation(int id, Storage fromStorage, Source toSource, BigDecimal amount, java.util.Currency currency) {
        super(id);
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.amount = amount;
        Currency = currency;
    }

    public OutcomeOperation(int id, Calendar date, String addInfo, Storage fromStorage, Source toSource, BigDecimal amount, java.util.Currency currency) {
        super(id, date, addInfo);
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.amount = amount;
        Currency = currency;
    }

    /**
     * Из какого хранилища уйдут средства
     */
    private Storage fromStorage;

    /**
     * Куда пошли средства
     */
    private Source toSource;

    /**
     * Сумма которая пришла
     */
    private BigDecimal amount;

    /**
     * Валюта в которой пришла
     */
    private Currency Currency;

    public Storage getFromStorage() {
        return fromStorage;
    }

    public void setFromStorage(Storage fromStorage) {
        this.fromStorage = fromStorage;
    }

    public Source getToSource() {
        return toSource;
    }

    public void setToSource(Source toSource) {
        this.toSource = toSource;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public java.util.Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(java.util.Currency currency) {
        Currency = currency;
    }
}
