package ru.makovey.financeCore.decorator;

import java.util.List;

import ru.makovey.financeCore.dao.interfaces.SourceDAO;
import ru.makovey.financeCore.interfaces.Source;

public class SourceSync implements SourceDAO {

    private SourceDAO sourceDAO;
    private List<Source> sourceList;

    public SourceSync(SourceDAO sourceDAO) {
        this.sourceDAO = sourceDAO;
    }

    @Override
    public List<Source> getAll() {
        if (sourceList == null) {
            sourceList = sourceDAO.getAll();
        }
        return sourceList;
    }

    @Override
    public Source get(int id) {
        return null;
    }

    @Override
    public boolean update(Source source) {
        if (sourceDAO.update(source)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Source source) {
        // TODO добавить нужные Exceptions
        if (sourceDAO.delete(source)) {
            sourceList.remove(source);
            return true;
        }
        return false;
    }

}
