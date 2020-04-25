package ru.makovey.financeCore.dao.impls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.makovey.financeCore.dao.interfaces.SourceDAO;
import ru.makovey.financeCore.database.SQLConnection;
import ru.makovey.financeCore.enums.OperationTypeEnum;
import ru.makovey.financeCore.impls.DefaultSource;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.util.TreeBuilder;

//TODO реализовать абстрактный класс для этих методов
public class SourceDAOImpl implements SourceDAO {

    private static final String SOURCE_TABLE = "source";

    private TreeBuilder<Source> treeBuilder = new TreeBuilder<>();

    private List<Source> sourceList = new ArrayList<>();


    @Override
    public List<Source> getAll() {
        sourceList.clear();
        try (Statement stmt = SQLConnection.getConncetion().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM " + SOURCE_TABLE)) {
            while(rs.next()){
                DefaultSource defaultSource = new DefaultSource();
                defaultSource.setId(rs.getInt("id"));
                defaultSource.setName(rs.getString("name"));

                int parentId = rs.getInt("parent_id");
                int operationTypeId = rs.getInt("operation_type_id");

                defaultSource.setOperationType(OperationTypeEnum.getType(operationTypeId));

                treeBuilder.addToTree(parentId, defaultSource, sourceList);
            }

            return sourceList; // содержит только корневые элементы
        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public Source get(int id) {
        return null;
    }


    @Override
    public boolean update(Source source) {
        try(PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("UPDATE" + SOURCE_TABLE + " set name=? where id=?")) {
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
        try(PreparedStatement stmt = SQLConnection.getConncetion().prepareStatement("DELETE FROM " + SOURCE_TABLE + " where id=?")) {
            stmt.setInt(1, source.getId());

            if (stmt.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            Logger.getLogger(StorageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }
}
