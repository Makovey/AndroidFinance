package ru.makovey.financeCore.impls.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Операция "Конвертация"
 */
public class ConvertOperation extends AbstractOperation {

    public ConvertOperation(){}

    public ConvertOperation(Storage fromStorage, Storage toStorage, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        this.fromStorage = fromStorage;
        this.toStorage = toStorage;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public ConvertOperation(int id, Storage fromStorage, Storage toStorage, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        super(id);
        this.fromStorage = fromStorage;
        this.toStorage = toStorage;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public ConvertOperation(int id, Calendar date, String addInfo, Storage fromStorage, Storage toStorage, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        super(id, date, addInfo);
        this.fromStorage = fromStorage;
        this.toStorage = toStorage;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    /**
     * Откуда взять средства
     */
    private Storage fromStorage;

    /**
     * Куда отправить средства
     */
    private Storage toStorage;

    /**
     * Из какой валюты конвертация
     */
    private Currency fromCurrency;

    /**
     * В какую валюты конвертация
     */
    private Currency toCurrency;

    /**
     * Сумма какую будем конвертировать
     */
    private BigDecimal fromAmount;

    /**
     * Сумма в которую будем конвертировать
     */
    private BigDecimal toAmount;

    public Storage getFromStorage() {
        return fromStorage;
    }

    public void setFromStorage(Storage fromStorage) {
        this.fromStorage = fromStorage;
    }

    public Storage getToStorage() {
        return toStorage;
    }

    public void setToStorage(Storage toStorage) {
        this.toStorage = toStorage;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }
}
