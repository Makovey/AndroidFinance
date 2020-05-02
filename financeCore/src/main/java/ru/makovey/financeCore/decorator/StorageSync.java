package ru.makovey.financeCore.decorator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.makovey.financeCore.dao.interfaces.StorageDAO;
import ru.makovey.financeCore.exceptions.AmountException;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.interfaces.Storage;
import ru.makovey.financeCore.util.TreeBuilder;

/**
 * Декоратор для StorageDAO
 * Синхронизирует работу БД с основной коллекцией
 */
public class StorageSync implements StorageDAO {

    private StorageDAO storageDAO;
    private List<Storage> storageList;
    private List<Storage> treeList = new ArrayList<>();
    private TreeBuilder<Storage> treeBuilder = new TreeBuilder<>(); // хранит дерево объектов
    private Map<Integer, Storage> identityMap = new HashMap<>(); // список коллекций, нужна для быстрого получения объекта по id и не обращаться к БД


    public StorageSync(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
        initCollection();
    }

    private void initCollection() {
        storageList = storageDAO.getAll();

        for (Storage s : storageList) {
            identityMap.put(s.getId(), s);
            treeBuilder.addToTree(s.getParentId(), s, treeList);
        }
    }

    @Override
    public boolean addCurrency(Storage storage, Currency currency, BigDecimal amount) throws CurrencyException {
        if (storageDAO.addCurrency(storage, currency, amount)) {
            storage.addCurrency(currency, amount);
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
    public boolean updateAmount(Storage storage, Currency currency, BigDecimal amount) throws AmountException, CurrencyException {
        //TODO продумать как откатить изменения в случае неудачи
        if (storageDAO.updateAmount(storage, currency, amount)) {
            storage.updateAmount(amount, currency);
        }
        return false;
    }

    @Override
    public List<Storage> getAll() {
        if (storageList == null) {
            storageDAO.getAll();
        }
        return storageList;
    }

    @Override
    public Storage get(int id) {
        return identityMap.get(id);
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

    @Override
    public boolean add(Storage object) {
        return false;
    }

    private void addToCollections(Storage storage) {
        identityMap.put(storage.getId(), storage);

        if (storage.hasParent()) {
            if (!storage.getParent().getChilds().contains(storage)) { // если это ребенок и он раннее не был добавлен
                storage.getParent().addChild(storage);
            }
        } else { // если элемент корневой
            treeList.add(storage);
        }
    }

    private void removeFromCollections(Storage storage) {
        identityMap.remove(storage.getId(), storage);
        if (storage.hasParent()) { // если это дочерний `элемент
            storage.getParent().removeChild(storage);
        } else { // если элемент корневой
            treeList.remove(storage);
        }
    }
}
