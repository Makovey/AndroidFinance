package ru.makovey.financeCore.dao.interfaces;

import java.math.BigDecimal;
import java.util.Currency;

import ru.makovey.financeCore.interfaces.Storage;

public interface StorageDAO extends CommonDAO<Storage> {
    /**
     * Добавить валюту
     * @param storage
     * @param currency
     * @return true || false (для последующих проверок)
     */
    boolean addCurrency(Storage storage, Currency currency);

    /**
     * Удалить валюту
     * @param storage
     * @param currency
     * @return true || false (для последующих проверок)
     */
    boolean deleteCurrency(Storage storage, Currency currency);

    /**
     * Обновить сумму. Удалить, добавить и т.д
     * @param storage
     * @param amount
     * @return true || false (для последующих проверок)
     */
    boolean updateAmount(Storage storage, BigDecimal amount);
}
