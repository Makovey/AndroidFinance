package ru.makovey.financeCore.interfaces;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public interface Storage {

    /**
     * Метод получения имени хранилища
     * @return имя хранилища
     */
    String getName();



    // Работа с валютой
    /**
     * Метод добавления валюты в хранилище
     * @param currency
     */
    void addCurrency(Currency currency);


    /**
     * Метод удаления валюты из хранилища
     * @param currency
     */
    void deleteCurrency(Currency currency);


    /**
     * Метод получения валюты по международному коду
     * @param code
     */
    Currency getCurrency(String code);

    /**
     * Метод возвращения всех доступных валют
     * @return список валют
     */
    List<Currency> getcurrencyList();



    // Изменение баланса
    /**
     * Метод изменения баланса по определенной валюте
     * @param amount - остаток
     * @param currency - сама валюта
     */
    void changeAmount(BigDecimal amount, Currency currency);


    /**
     * Метод добавления суммы в остаток
     * @param amount - сумма добавления
     * @param currency - валюта
     */
    void addAmount(BigDecimal amount, Currency currency);


    /**
     * Метод вычитания суммы в валюте
     * @param amount - сумма вычитания
     * @param currency - валюта
     */
    void expenseAmount(BigDecimal amount, Currency currency);



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
    BigDecimal getAmount(Currency currency);


    /**
     * Метод получения примерного остатка всех денег переведенных в одну валюту
     * @return остаток
     */
    BigDecimal getApproxAmount();
}
