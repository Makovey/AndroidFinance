package ru.makovey.financeCore.interfaces;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import ru.makovey.financeCore.exceptions.AmountException;
import ru.makovey.financeCore.exceptions.CurrencyException;

public interface Storage extends CompositeTree{

    // Работа с валютой
    /**
     * Метод добавления валюты в хранилище
     * @param currency
     */
    void addCurrency(Currency currency, BigDecimal amount) throws CurrencyException;


    /**
     * Метод удаления валюты из хранилища
     * @param currency
     */
    void deleteCurrency(Currency currency) throws CurrencyException;


    /**
     * Метод получения валюты по международному коду
     * @param code
     */
    Currency getCurrency(String code);

    /**
     * Метод возвращения всех доступных валют
     * @return список валют
     */
    List<Currency> getCurrencyList();



    // изменение баланса
    /**
     * Метод вычитания суммы в валюте
     * @param amount - сумма вычитания
     * @param currency - валюта
     */
    void updateAmount(BigDecimal amount, Currency currency) throws CurrencyException, AmountException;



    // Получение баланса
    /**
     * Метод получения остатка по каждой доступной валюте в хранилище
     * @return остаток
     */
    Map<Currency, BigDecimal> getCurrencyAmounts();


    /**
     * Метод получения остатка по опереденной валюте
     * @param currency - валюта
     * @return остаток
     */
    BigDecimal getAmount(Currency currency) throws CurrencyException;


    /**
     * Метод получения примерного остатка всех денег переведенных в одну валюту
     * @return остаток
     */
    BigDecimal getApproxAmount();
}
