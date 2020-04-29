package ru.makovey.financeCore.dao.impls;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.makovey.financeCore.dao.interfaces.StorageDAO;
import ru.makovey.financeCore.database.SQLConnection;
import ru.makovey.financeCore.impls.DefaultStorage;
import ru.makovey.financeCore.interfaces.Storage;

public class StorageDAOImpl implements StorageDAO {

    private static final String CURRENCY_TABLE = "currency_amount";
    private static final String STORAGE_TABLE = "storage";

    private List<Storage> storageList = new ArrayList<>();


    @Override
    public boolean addCurrency(Storage storage, Currency currency) {
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("INSERT INTO " + CURRENCY_TABLE + " (currency_code, storage_id) VALUES (?,?)")) {

            stmt.setString(1, currency.getCurrencyCode());
            stmt.setInt(2, storage.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        //TODO вместо false выкидывать Exception
        return false;
    }

    @Override
    public boolean deleteCurrency(Storage storage, Currency currency) {
        //TODO реализовать - если есть операции по валюте - запретить удаление
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("DELETE FROM" + CURRENCY_TABLE + " WHERE storage_id=? and currency_code=?")) {
            stmt.setInt(1, storage.getId());
            stmt.setString(2, currency.getCurrencyCode());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    @Override
    public boolean updateAmount(Storage storage, Currency currency, BigDecimal amount) {
        return false;
    }

    @Override
    public List<Storage> getAll() {
        storageList.clear();
        try (Statement stmt = SQLConnection.getConncetion().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM " + STORAGE_TABLE + " ORDER BY parent_id")) {
            while(rs.next()){
                DefaultStorage defaultStorage = new DefaultStorage();
                defaultStorage.setId(rs.getInt("id"));
                defaultStorage.setName(rs.getString("name"));
                defaultStorage.setParentId(rs.getInt("parent_id"));
                storageList.add(defaultStorage);
            }

            return storageList;
        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public Storage get(int id) {
        return null;
    }

    @Override
    public boolean update(Storage storage) {
        try(PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("UPDATE" + STORAGE_TABLE + " set name=? where id=?")) {
            stmt.setString(1, storage.getName());
            stmt.setInt(2, storage.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    @Override
    public boolean delete(Storage storage) {
        //TODO если у хранилища есть операции - запретить удаление
        try(PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("DELETE FROM " + STORAGE_TABLE + " where id=?")) {
            stmt.setInt(1, storage.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }
}
