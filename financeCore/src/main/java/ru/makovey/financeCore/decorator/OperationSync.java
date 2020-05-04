package ru.makovey.financeCore.decorator;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.makovey.financeCore.dao.interfaces.OperationDAO;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.exceptions.AmountException;
import ru.makovey.financeCore.exceptions.CurrencyException;
import ru.makovey.financeCore.impls.operations.ConvertOperation;
import ru.makovey.financeCore.impls.operations.IncomeOperation;
import ru.makovey.financeCore.impls.operations.OutcomeOperation;
import ru.makovey.financeCore.impls.operations.TransferOperation;
import ru.makovey.financeCore.interfaces.Operation;

public class OperationSync implements OperationDAO {

    private OperationDAO operationDAO;

    private List<Operation> operationList;
    private Map<OperationType, List<Operation>> operationMap = new EnumMap<>(OperationType.class);
    private Map<Integer, Operation> identityMap = new HashMap<>(); // список коллекций, нужна для быстрого получения объекта по id и не обращаться к БД

    private StorageSync storageSync;
    private SourceSync sourceSync;

    public OperationSync(OperationDAO operationDAO, StorageSync storageSync, SourceSync sourceSync) {
        this.operationDAO = operationDAO;
        this.storageSync = storageSync;
        this.sourceSync = sourceSync;
        initCollection();
    }

    private void initCollection() {
        operationList = operationDAO.getAll();

        for (Operation operation : operationList) {
            identityMap.put(operation.getId(), operation);
        }

        fillOperationMap(); // разделение по типам операции
    }

    private void fillOperationMap() {
        for (OperationType type : OperationType.values()) {
            operationMap.put(type, operationList.stream().filter(o -> o.getOperationType() == type).collect(Collectors.toList()));
        }
    }


    @Override
    public List<Operation> getList(OperationType operationType) {
        return operationMap.get(operationType);
    }

    @Override
    public List<Operation> getAll() {
        return operationList;
    }

    @Override
    public Operation get(int id) {
        return identityMap.get(id);
    }

    @Override
    public boolean update(Operation object) {
        if (operationDAO.update(object)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Operation object) {
        if (operationDAO.delete(object) && revertBalance(object)) { // если в БД удалилось без проблем
            removeFromCollections(object);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Operation object) {
        if (operationDAO.add(object)) {
            addToCollections(object);

            boolean updateAmountResult = false;

            try {
                switch (object.getOperationType()) {
                    case INCOME: {
                        IncomeOperation incomeOperation = (IncomeOperation) object;

                        BigDecimal currentAmount = incomeOperation.getToStorage().getAmount(incomeOperation.getFromCurrency()); // текущий баланс
                        BigDecimal newAmount = currentAmount.add(incomeOperation.getFromAmount()); // прибавляем новое значение

                        // обновляем баланс
                        updateAmountResult = storageSync.updateAmount(incomeOperation.getToStorage(), incomeOperation.getFromCurrency(), newAmount);

                        break;
                    }
                    case OUTCOME: {
                        OutcomeOperation outcomeOperation = (OutcomeOperation) object;

                        BigDecimal currentAmount = outcomeOperation.getFromStorage().getAmount(outcomeOperation.getFromCurrency());
                        BigDecimal newAmount = currentAmount.subtract(outcomeOperation.getFromAmount());

                        updateAmountResult = storageSync.updateAmount(outcomeOperation.getFromStorage(), outcomeOperation.getFromCurrency(), newAmount);

                        break;
                    }
                    case TRANSFER: {
                        TransferOperation transferOperation = (TransferOperation) object;

                        // из хранилища (откуда) отнимаем сумму
                        BigDecimal currentAmountFromStorage = transferOperation.getFromStorage().getAmount(transferOperation.getFromCurrency());
                        BigDecimal newAmountFromStorage = currentAmountFromStorage.subtract(transferOperation.getFromAmount());

                        // в хранилище (куда) прибавляем сумму
                        BigDecimal currentAmountToStorage = transferOperation.getToStorage().getAmount(transferOperation.getFromCurrency());
                        BigDecimal newAmountToStorage = currentAmountToStorage.add(transferOperation.getFromAmount());

                        // обновляем баланс в обоих хранилищах
                        updateAmountResult = storageSync.updateAmount(transferOperation.getFromStorage(), transferOperation.getFromCurrency(), newAmountFromStorage) &&
                                storageSync.updateAmount(transferOperation.getToStorage(), transferOperation.getFromCurrency(), newAmountToStorage);

                        break;
                    }
                    case CONVERT: {
                        ConvertOperation convertOperation = (ConvertOperation) object;

                        BigDecimal currentAmountFromStorage = convertOperation.getFromStorage().getAmount(convertOperation.getFromCurrency());
                        BigDecimal newAmountFromStorage = currentAmountFromStorage.subtract(convertOperation.getFromAmount());

                        BigDecimal currentAmountToStorage = convertOperation.getToStorage().getAmount(convertOperation.getToCurrency());
                        BigDecimal newAmountToStorage = currentAmountToStorage.add(convertOperation.getToAmount());

                        updateAmountResult = storageSync.updateAmount(convertOperation.getFromStorage(), convertOperation.getFromCurrency(), newAmountFromStorage) &&
                                storageSync.updateAmount(convertOperation.getToStorage(), convertOperation.getFromCurrency(), newAmountToStorage);

                        break;
                    }
                }
            } catch (CurrencyException | AmountException e) {
                e.printStackTrace();
            }

            if (!updateAmountResult) {
                delete(object);
                return false;
            }
        }

        return false;
    }

    /**
     * Откатываем баланс, в зависимости от операции
     *
     * @param object - сама операция
     * @return
     */
    private boolean revertBalance(Operation object) {
        boolean updateAmountResult = false;

        try {
            switch (object.getOperationType()) {
                case INCOME: { // если был доход - отнимаем сумму
                    IncomeOperation incomeOperation = (IncomeOperation) object;

                    BigDecimal currentAmount = incomeOperation.getToStorage().getAmount(incomeOperation.getFromCurrency()); // текущий баланс
                    BigDecimal newAmount = currentAmount.subtract(incomeOperation.getFromAmount()); // прибавляем новое значение

                    // обновляем баланс
                    updateAmountResult = storageSync.updateAmount(incomeOperation.getToStorage(), incomeOperation.getFromCurrency(), newAmount);

                    break;
                }
                case OUTCOME: { // если был расход - прибавляем сумму
                    OutcomeOperation outcomeOperation = (OutcomeOperation) object;

                    BigDecimal currentAmount = outcomeOperation.getFromStorage().getAmount(outcomeOperation.getFromCurrency());
                    BigDecimal newAmount = currentAmount.add(outcomeOperation.getFromAmount());

                    updateAmountResult = storageSync.updateAmount(outcomeOperation.getFromStorage(), outcomeOperation.getFromCurrency(), newAmount);

                    break;
                }
                case TRANSFER: { // если был перевод - отправляем назад в хранилище
                    TransferOperation transferOperation = (TransferOperation) object;

                    // из хранилища (откуда) отнимаем сумму
                    BigDecimal currentAmountFromStorage = transferOperation.getFromStorage().getAmount(transferOperation.getFromCurrency());
                    BigDecimal newAmountFromStorage = currentAmountFromStorage.add(transferOperation.getFromAmount());

                    // в хранилище (куда) прибавляем сумму
                    BigDecimal currentAmountToStorage = transferOperation.getToStorage().getAmount(transferOperation.getFromCurrency());
                    BigDecimal newAmountToStorage = currentAmountToStorage.subtract(transferOperation.getFromAmount());

                    // обновляем баланс в обоих хранилищах
                    updateAmountResult = storageSync.updateAmount(transferOperation.getFromStorage(), transferOperation.getFromCurrency(), newAmountFromStorage) &&
                            storageSync.updateAmount(transferOperation.getToStorage(), transferOperation.getFromCurrency(), newAmountToStorage);

                    break;
                }
                case CONVERT: { // если была конвертация - с одного хранилища отнимаем по его валюте, в другое прибавляем тоже по его валюте
                    ConvertOperation convertOperation = (ConvertOperation) object;

                    BigDecimal currentAmountFromStorage = convertOperation.getFromStorage().getAmount(convertOperation.getFromCurrency());
                    BigDecimal newAmountFromStorage = currentAmountFromStorage.add(convertOperation.getFromAmount());

                    BigDecimal currentAmountToStorage = convertOperation.getToStorage().getAmount(convertOperation.getToCurrency());
                    BigDecimal newAmountToStorage = currentAmountToStorage.subtract(convertOperation.getToAmount());

                    updateAmountResult = storageSync.updateAmount(convertOperation.getFromStorage(), convertOperation.getFromCurrency(), newAmountFromStorage) &&
                            storageSync.updateAmount(convertOperation.getToStorage(), convertOperation.getFromCurrency(), newAmountToStorage);

                    break;
                }
            }
        } catch (CurrencyException | AmountException e) {
            e.printStackTrace();
        }

        if (!updateAmountResult) {
            delete(object);// откатываем созданную операцию
            return false;
        }

        return true;
    }

    private void addToCollections(Operation operation) {
        operationList.add(operation);
        identityMap.put(operation.getId(), operation);
        operationDAO.getList(operation.getOperationType()).add(operation);
    }

    private void removeFromCollections(Operation operation) {
        operationList.remove(operation);
        identityMap.remove(operation.getId(), operation);
        operationDAO.getList(operation.getOperationType()).remove(operation);
    }
}
