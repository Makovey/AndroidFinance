package ru.makovey.financeCore.dao.interfaces;

import java.math.BigDecimal;
import java.util.Currency;

import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Storage;

public interface StorageDAO extends CommonDAO<Storage> {
    /**
     * Добавить валюту
     * @param storage
     * @param currency
     * @return true || false (для последующих проверок)
     */
    boolean addCurrency(Storage storage, Currency currency, BigDecimal amount) throws CurrencyException;

    /**
     * Удалить валюту
     * @param storage
     * @param currency
     * @return true || false (для последующих проверок)
     */
    boolean deleteCurrency(Storage storage, Currency currency) throws CurrencyException;

    /**
     * Обновить сумму. Удалить, добавить и т.д
     * @param storage
     * @param amount
     * @param currency
     * @return true || false (для последующих проверок)
     */
    boolean updateAmount(Storage storage, Currency currency, BigDecimal amount);
}
