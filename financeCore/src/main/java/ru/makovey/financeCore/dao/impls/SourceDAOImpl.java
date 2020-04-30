package ru.makovey.financeCore.dao.impls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.makovey.financeCore.dao.interfaces.SourceDAO;
import ru.makovey.financeCore.database.SQLConnection;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.impls.DefaultSource;
import ru.makovey.financeCore.interfaces.Source;

//TODO реализовать абстрактный класс для этих методов
public class SourceDAOImpl implements SourceDAO {

    private static final String SOURCE_TABLE = "source";

    private List<Source> sourceList = new ArrayList<>();

    private Map<OperationType, List<Source>> sourceMap = new EnumMap<>(OperationType.class);


    @Override
    public List<Source> getAll() {
        sourceList.clear();
        try (Statement stmt = SQLConnection.getConncetion().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + SOURCE_TABLE + " order by parent_id")) {
            while (rs.next()) {
                DefaultSource defaultSource = new DefaultSource();
                defaultSource.setId(rs.getInt("id"));
                defaultSource.setName(rs.getString("name"));
                defaultSource.setParentId(rs.getInt("parent_id"));
                defaultSource.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                sourceList.add(defaultSource);
            }
            return sourceList;
        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


    @Override
    public Source get(int id) {
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("SELECT * FROM " + SOURCE_TABLE + " WHERE id=?")) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultSource source = null;

                if (rs.next()) {
                    source = new DefaultSource();
                    source.setId(rs.getInt("id"));
                    source.setName(rs.getString("name"));
                    source.setParentId(rs.getInt("parent_id"));
                    source.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                }
                return source;
            }

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }


    @Override
    public boolean update(Source source) {
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("UPDATE" + SOURCE_TABLE + " SET name=? WHERE id=?")) {
            stmt.setString(1, source.getName());
            stmt.setInt(2, source.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    @Override
    public boolean delete(Source source) {
        //TODO если у хранилища есть операции - запретить удаление
        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("DELETE FROM " + SOURCE_TABLE + " WHERE id=?")) {
            stmt.setInt(1, source.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    @Override
    public List<Source> getList(OperationType operationType) {
        sourceList.clear();

        try (PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("SELECT * FROM " + SOURCE_TABLE + " WHERE operation_type_id=?")) {
            stmt.setInt(1, operationType.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultSource source = null;

                if (rs.next()) {
                    source = new DefaultSource();
                    source.setId(rs.getInt("id"));
                    source.setName(rs.getString("name"));
                    source.setParentId(rs.getInt("parent_id"));
                    source.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                    sourceList.add(source);
                }
                return sourceList;

            }

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public boolean add(Source object) {
        try (PreparedStatement stmt = SQLConnection.getConncetion().
                prepareStatement("INSERT INTO " + SOURCE_TABLE + " (name, parent_id, operation_type_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, object.getName());

            if (object.hasParent()) {
                stmt.setInt(2, object.getParent().getId());
            } else {
                stmt.setNull(2, Types.NULL);
            }

            stmt.setInt(3, object.getOperationType().getId());

            if (stmt.executeUpdate() == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) { // получаем id вставленной записи
                    if (rs.next()) {
                        object.setId(rs.getInt(1)); // присваиваем новый id в объект
                    }
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
