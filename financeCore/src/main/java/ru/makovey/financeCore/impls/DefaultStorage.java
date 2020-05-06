package ru.makovey.financeCore.impls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.makovey.financeCore.abstracts.AbstractCompositeTree;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Storage;

public class DefaultStorage extends AbstractCompositeTree implements Storage {

    public DefaultStorage(){}

    public DefaultStorage(String name){
        super(name);
    }

    public DefaultStorage(String name, int id) {
        super(name, id);
    }

    public DefaultStorage(String name, Map<Currency, BigDecimal> currencyAmounts, List<Currency> currencyList) {
        super(name);
        this.currencyAmounts = currencyAmounts;
        this.currencyList = currencyList;
    }

    public DefaultStorage(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public DefaultStorage(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }



    private Map<Currency, BigDecimal> currencyAmounts = new HashMap<>();

    // Коллекция необходима для получеиня только валют, без лишней информации
    private List<Currency> currencyList = new ArrayList<>();

    @Override
    public Map<Currency, BigDecimal> getCurrencyAmounts() {
        return currencyAmounts;
    }

    public void setCurrencyAmounts(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }

    @Override
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setcurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public void updateAmount(BigDecimal amount, Currency currency) throws CurrencyException {
        checkCurrencyExist(currency);
        currencyAmounts.put(currency, amount);
    }

    @Override
    public void addCurrency(Currency currency, BigDecimal amount) throws CurrencyException {
        if (currencyAmounts.containsKey(currency)) {
            throw new CurrencyException("Currency " + currency + " alreay exist.");
        }

        // установка ключа, с пока еще пустым значением
        currencyAmounts.put(currency, amount);
        currencyList.add(currency);
    }

    @Override
    public void deleteCurrency(Currency currency) throws CurrencyException {
        // Проверка, есть ли такая валюта
        checkCurrencyExist(currency);

        // Если на балансе есть деньги, не даем удалить эту валюту
//        if (currencyAmounts.get(currency).equals(BigDecimal.ZERO)) {
//            throw new CurrencyException("Can't delete currency with amount.");
//        }
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
