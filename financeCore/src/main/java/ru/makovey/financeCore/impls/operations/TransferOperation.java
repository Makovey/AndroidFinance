package ru.makovey.financeCore.impls.operations;

import java.math.BigDecimal;
import java.util.Currency;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Операция "Перевод"
 */
public class TransferOperation extends AbstractOperation {

    public TransferOperation(){
        super(OperationType.TRANSFER);
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
     * Сумма которую будем перечислять
     */
    private BigDecimal fromAmount;

    /**
     * В какой валюте будем перечислять
     */
    private Currency fromCurrency;

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

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
}
