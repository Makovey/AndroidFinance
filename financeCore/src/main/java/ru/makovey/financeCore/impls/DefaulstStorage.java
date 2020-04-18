package ru.makovey.financeCore.impls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.makovey.financeCore.exceptions.AmountException;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Storage;

public class DefaulstStorage implements Storage {

    DefaulstStorage(){}

    public DefaulstStorage(String name){
        this.name = name;
    }

    public DefaulstStorage(String name, Map<Currency, BigDecimal> currencyAmounts, List<Currency> currencyList) {
        this.name = name;
        this.currencyAmounts = currencyAmounts;
        this.currencyList = currencyList;
    }

    public DefaulstStorage(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public DefaulstStorage(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }

    private String name;
    private Map<Currency, BigDecimal> currencyAmounts = new HashMap<>();

    // Коллекция необходима для получеиня только валют, без лишней информации
    private List<Currency> currencyList = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<Currency, BigDecimal> getCurrencyAmounts() {
        return currencyAmounts;
    }

    public void setCurrencyAmounts(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }

    @Override
    public List<Currency> getcurrencyList() {
        return currencyList;
    }

    public void setcurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    // Происходит обновление баланса
    @Override
    public void changeAmount(BigDecimal amount, Currency currency) throws CurrencyException {
        checkCurrencyExist(currency);
        currencyAmounts.put(currency, amount);
    }

    @Override
    public void addCurrency(Currency currency) throws CurrencyException {
        if (currencyAmounts.containsKey(currency)) {
            throw new CurrencyException("Currency " + currency + " alreay exist.");
        }

        // установка ключа, с пока еще пустым значением
        currencyAmounts.put(currency, BigDecimal.ZERO);
        currencyList.add(currency);
    }

    @Override
    public void deleteCurrency(Currency currency) throws CurrencyException {
        // Проверка, есть ли такая валюта
        checkCurrencyExist(currency);

        // Если на балансе есть деньги, не даем удалить эту валюту
        if (currencyAmounts.get(currency).equals(BigDecimal.ZERO)) {
            throw new CurrencyException("Can't delete currency with amount.");
        }


        currencyAmounts.remove(currency);
        currencyList.remove(currency);

    }


    @Override
    public Currency getCurrency(String code) {
        for (Currency currency : currencyList) {
            if (currency.getCurrencyCode().equals(code)) {
                return currency;
            }
        }
        return null;
    }

    @Override
    public void addAmount(BigDecimal amount, Currency currency) throws CurrencyException {
        checkCurrencyExist(currency);
        // Вытаскиваем старое значение и добавляем новое
        currencyAmounts.put(currency, currencyAmounts.get(currency).add(amount));
    }

    @Override
    public void expenseAmount(BigDecimal amount, Currency currency) throws CurrencyException, AmountException {
        checkCurrencyExist(currency);
        if (currencyAmounts.get(currency).compareTo(amount) < 0)
            throw new AmountException("Amount can't be < 0.");
        // Вытаскиваем старое значение, вычитаем с новым и записываем в Map
        currencyAmounts.put(currency, currencyAmounts.get(currency).subtract(amount));
    }

    @Override
    public BigDecimal getAmount(Currency currency) throws CurrencyException {
        checkCurrencyExist(currency);
        return currencyAmounts.get(currency);
    }

    @Override
    public BigDecimal getApproxAmount() {
        //TODO реализовать метод
        throw new UnsupportedOperationException("NOT REALIZED");
    }

    //Проверка существует ли такая валюта в данном хранилище
    private void checkCurrencyExist(Currency currency) throws CurrencyException {
        if (!currencyAmounts.containsKey(currency)) {
            throw new CurrencyException("Currency " + currency + " not exist.");
        }
    }
}
