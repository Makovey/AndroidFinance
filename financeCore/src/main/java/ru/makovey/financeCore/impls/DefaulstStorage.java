package ru.makovey.financeCore.impls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.makovey.financeCore.interfaces.Storage;

public class DefaulstStorage implements Storage {

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
    public void changeAmount(BigDecimal amount, Currency currency) {
        if (!currencyAmounts.containsKey(currency))
            throw new NullPointerException("Такой валюты не существует");
        currencyAmounts.put(currency, amount);
    }

    @Override
    public void addCurrency(Currency currency) {
        // установка ключа, с пока еще пустым значением
        currencyAmounts.put(currency, BigDecimal.ZERO);
        currencyList.add(currency);
    }

    @Override
    public void deleteCurrency(Currency currency) {
        if (!currencyAmounts.containsKey(currency)) throw new NullPointerException("Такой валюты не существует");
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
    public void addAmount(BigDecimal amount, Currency currency) {
        if (!currencyAmounts.containsKey(currency)) throw new NullPointerException("Такой валюты не существует");
        // Вытаскиваем старое значение и добавляем новое
        currencyAmounts.put(currency, currencyAmounts.get(currency).add(amount));
    }

    @Override
    public void expenseAmount(BigDecimal amount, Currency currency) {
        if (!currencyAmounts.containsKey(currency)) throw new NullPointerException("Такой валюты не существует");
        if(currencyAmounts.get(currency).compareTo(amount) < 0) throw new ArithmeticException("Баланс ушел в минус");
        // Вытаскиваем старое значение, вычитаем с новым и записываем в Map
        currencyAmounts.put(currency, currencyAmounts.get(currency).subtract(amount));
    }

    @Override
    public BigDecimal getAmount(Currency currency) {
        return currencyAmounts.get(currency);
    }

    @Override
    public BigDecimal getApproxAmount() {
        //TODO реализовать метод
        throw new UnsupportedOperationException("NOT REALIZED");
    }
}
