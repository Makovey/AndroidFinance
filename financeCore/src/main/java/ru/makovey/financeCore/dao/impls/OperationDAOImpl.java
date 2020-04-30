package ru.makovey.financeCore.dao.impls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.makovey.financeCore.abstracts.AbstractOperation;
import ru.makovey.financeCore.dao.interfaces.OperationDAO;
import ru.makovey.financeCore.database.SQLConnection;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.impls.operations.ConvertOperation;
import ru.makovey.financeCore.impls.operations.IncomeOperation;
import ru.makovey.financeCore.impls.operations.OutcomeOperation;
import ru.makovey.financeCore.impls.operations.TransferOperation;
import ru.makovey.financeCore.interfaces.Operation;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.interfaces.Storage;

public class OperationDAOImpl implements OperationDAO {

    private static final String OPERATION_TABLE = "operation";
    private List<Operation> operationList = new ArrayList<>();

    // чтоб не получать эти объекты из БД, передаем сюда
    private Map<Integer, Source> sourceIdentityMap;
    private Map<Integer, Storage> storageIdentityMap;

    public OperationDAOImpl(Map<Integer, Source> sourceIdentityMap, Map<Integer, Storage> storageIdentityMap) {
        this.sourceIdentityMap = sourceIdentityMap;
        this.storageIdentityMap = storageIdentityMap;
    }

    @Override
    public List<Operation> getList(OperationType operationType) {
        return null;
    }

    @Override
    public List<Operation> getAll() {
        operationList.clear();

        try (Statement stmt = SQLConnection.getConncetion().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + OPERATION_TABLE)) {

            while (rs.next()) {
                operationList.add(fillOperation(rs));
            }

        } catch (SQLException e) {
            Logger.getLogger(OperationDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    private AbstractOperation fillOperation(ResultSet rs) throws SQLException {
        OperationType operationType = OperationType.getType(rs.getInt("type_id"));

        // заполняем сцецифичные поля
        AbstractOperation operation = createOperation(operationType, rs);

        //заполняем общие поля
        operation.setId(rs.getInt("id"));
        operation.setOperationType(operationType);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(rs.getInt("datetime"));

        operation.setDate(calendar);
        operation.setDescription(rs.getString("description"));

        return operation;
    }

    private AbstractOperation createOperation(OperationType operationType, ResultSet rs) throws SQLException {
        switch (operationType) {
            case INCOME: {
                IncomeOperation incomeOperation = new IncomeOperation();

                incomeOperation.setFromSource(sourceIdentityMap.get(rs.getInt("from_source_id"))); // откуда поступили деньги
                incomeOperation.setFromCurrency(Currency.getInstance(rs.getString("from_currency_code"))); // в какой валюте поступили
                incomeOperation.setFromAmount(rs.getBigDecimal("from_amount")); // сумма поступления
                incomeOperation.setToStorage(storageIdentityMap.get(rs.getInt("to_storage_id")));// куда положим эти деньги

                return incomeOperation;
            }
            case OUTCOME: {
                OutcomeOperation outcomeOperation = new OutcomeOperation();

                outcomeOperation.setFromStorage(storageIdentityMap.get(rs.getInt("from_source_id"))); // откуда поступили деньги
                outcomeOperation.setFromCurrency(Currency.getInstance(rs.getString("from_currency_code"))); // в какой валюте поступили
                outcomeOperation.setFromAmount(rs.getBigDecimal("from_amount")); // сумма расхода
                outcomeOperation.setToSource(sourceIdentityMap.get(rs.getInt("to_storage_id")));// на что потратили

                return outcomeOperation;
            }
            case TRANSFER: {
                TransferOperation transferOperation = new TransferOperation();

                transferOperation.setFromStorage(storageIdentityMap.get(rs.getInt("from_storage_id")));// откуда переводим
                transferOperation.setFromCurrency(Currency.getInstance(rs.getString("from_currency_code"))); // в какой валюте переводим
                transferOperation.setFromAmount(rs.getBigDecimal("from_amount")); // какую сумму переводим
                transferOperation.setToStorage(storageIdentityMap.get(rs.getInt("to_storage_id"))); // не создаем новый объект, используем ранее созданный объект источника

                return transferOperation;
            }
            case CONVERT: {
                ConvertOperation convertOperation = new ConvertOperation();

                convertOperation.setFromStorage(storageIdentityMap.get(rs.getInt("from_storage_id"))); // откуда конвертируем
                convertOperation.setFromCurrency(Currency.getInstance(rs.getString("from_currency_code"))); // в каой валюте
                convertOperation.setFromAmount(rs.getBigDecimal("from_amount")); // какая сумма в исходной валюте


                convertOperation.setToStorage(storageIdentityMap.get(rs.getInt("to_storage_id"))); // куда конвертируем
                convertOperation.setToCurrency((Currency.getInstance(rs.getString("to_currency_code")))); // валюта поступления
                convertOperation.setToAmount(rs.getBigDecimal("to_amount")); // какая итоговая сумма поступила в этой валюте

                return convertOperation;
            }
        }

        // TODO Exception
        return null; // если ни один из типов не подошел
    }

    @Override
    public Operation get(int id) {
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("SELECT * FROM " + OPERATION_TABLE + " WHERE id=?")) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AbstractOperation operation =fillOperation(rs);
                    return operation;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(OperationDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    @Override
    public boolean update(Operation object) {
        return false;
    }

    @Override
    public boolean delete(Operation object) {
        return false;
    }

    @Override
    public boolean add(Operation object) {
        return false;
    }
}
