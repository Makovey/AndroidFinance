package ru.makovey.financeCore.impls.operations;

import java.math.BigDecimal;
import java.util.Currency;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Операция "Расход"
 */
public class OutcomeOperation extends AbstractOperation {

    public OutcomeOperation(){
        super(OperationType.OUTCOME);
    }

    /**
     * из какого хранилища уйдут средства
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
