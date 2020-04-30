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

    public OutcomeOperation(){}

    public OutcomeOperation(Storage fromStorage, Source toSource, BigDecimal fromAmount, java.util.Currency fromCurrency) {
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.fromAmount = fromAmount;
        this.fromCurrency = fromCurrency;
    }

    public OutcomeOperation(int id, Storage fromStorage, Source toSource, BigDecimal fromAmount, java.util.Currency fromCurrency) {
        super(id);
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.fromAmount = fromAmount;
        this.fromCurrency = fromCurrency;
    }

    public OutcomeOperation(int id, Calendar date, String addInfo, Storage fromStorage, Source toSource, BigDecimal fromAmount, java.util.Currency fromCurrency) {
        super(id, date, addInfo);
        this.fromStorage = fromStorage;
        this.toSource = toSource;
        this.fromAmount = fromAmount;
        this.fromCurrency = fromCurrency;
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
    private BigDecimal fromAmount;

    /**
     * Валюта в которой пришла
     */
    private Currency fromCurrency;

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

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public java.util.Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(java.util.Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
}
