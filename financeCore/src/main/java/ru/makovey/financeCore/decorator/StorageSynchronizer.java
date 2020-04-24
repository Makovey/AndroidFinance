package ru.makovey.financeCore.decorator;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import ru.makovey.financeCore.dao.interfaces.StorageDAO;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Storage;

/**
 * Декоратор для StorageDAO
 * Синхронизирует работу БД с основной коллекцией
 */
public class StorageSynchronizer implements StorageDAO {

    private StorageDAO storageDAO;
    private List<Storage> storageList;

    public StorageSynchronizer(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
        initCollection();
    }

    private void initCollection() {
        storageList = storageDAO.getAll();
    }

    @Override
    public boolean addCurrency(Storage storage, Currency currency) throws CurrencyException {
        if (storageDAO.addCurrency(storage, currency)) {
            storage.addCurrency(currency);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCurrency(Storage storage, Currency currency) throws CurrencyException {
        if (storageDAO.deleteCurrency(storage, currency)) {
            storage.deleteCurrency(currency);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAmount(Storage storage, Currency currency, BigDecimal amount) {
        //TODO продумать как откатить изменения в случае неудачи
        if (storageDAO.updateAmount(storage, currency, amount)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Storage> getAll() {
        if (storageList == null){
            storageDAO.getAll();
        }
        return storageList;
    }

    @Override
    public Storage get(int id) {
        return storageDAO.get(id);
    }

    @Override
    public boolean update(Storage storage) {
        if (storageDAO.update(storage)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Storage storage) {
        //TODO Exception
        if (storageDAO.delete(storage)) {
            storageList.remove(storage);
            return true;
        }
        return false;
    }
}
