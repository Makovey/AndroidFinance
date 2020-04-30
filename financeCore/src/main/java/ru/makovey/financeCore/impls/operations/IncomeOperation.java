package ru.makovey.financeCore.impls.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Операция "Доход"
 */
//TODO для всех классов создать конструкторы без id, так как будет autoincrement в бд
public class IncomeOperation extends AbstractOperation {
    public IncomeOperation() {
    }

    public IncomeOperation(Source fromSource, Storage toStorage, BigDecimal fromAmount, java.util.Currency currency) {
        this.fromSource = fromSource;
        this.toStorage = toStorage;
        this.fromAmount = fromAmount;
        fromCurrency = currency;
    }

    public IncomeOperation(int id, Source fromSource, Storage toStorage, BigDecimal fromAmount, java.util.Currency currency) {
        super(id);
        this.fromSource = fromSource;
        this.toStorage = toStorage;
        this.fromAmount = fromAmount;
        fromCurrency = currency;
    }

    public IncomeOperation(int id, Calendar date, String addInfo, Source fromSource, Storage toStorage, BigDecimal fromAmount, java.util.Currency currency) {
        super(id, date, addInfo);
        this.fromSource = fromSource;
        this.toStorage = toStorage;
        this.fromAmount = fromAmount;
        fromCurrency = currency;
    }

    /**
     * Откуда пришли средства
     */
    private Source fromSource;

    /**
     * Куда пошли средства
     */
    private Storage toStorage;

    /**
     * Сумма которая пришла
     */
    private BigDecimal fromAmount;

    /**
     * Валюта в которой пришла
     */
    private Currency fromCurrency;

    public Source getFromSource() {
        return fromSource;
    }

    public void setFromSource(Source fromSource) {
        this.fromSource = fromSource;
    }

    public Storage getToStorage() {
        return toStorage;
    }

    public void setToStorage(Storage toStorage) {
        this.toStorage = toStorage;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency currency) {
        fromCurrency = currency;
    }
}
