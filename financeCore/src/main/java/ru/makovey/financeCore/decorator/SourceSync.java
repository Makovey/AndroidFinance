package ru.makovey.financeCore.decorator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.makovey.financeCore.dao.interfaces.SourceDAO;
import ru.makovey.financeCore.enums.OperationType;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.util.TreeBuilder;

public class SourceSync implements SourceDAO {

    private SourceDAO sourceDAO;

    private TreeBuilder<Source> treeBuilder = new TreeBuilder<>();

    private List<Source> treeSource = new ArrayList<>(); // объекты хранятся в виде дерева
    private Map<Integer, Source> identityMap = new HashMap<>(); // список коллекций, нужна для быстрого получения объекта по id и не обращаться к БД
    private Map<OperationType, List<Source>> sourceMap = new EnumMap<>(OperationType.class); // дерево, разбито по типам операции

    public SourceSync(SourceDAO sourceDAO) {
        this.sourceDAO = sourceDAO;
        initCollection();
    }

    public void initCollection() {
        List<Source> sources = sourceDAO.getAll();

        for (Source s : sources) {
            identityMap.put(s.getId(), s);
            treeBuilder.addToTree(s.getParentId(), s, treeSource);
        }

        fillSourceMap(treeSource);
    }

    /**
     * Разделение коллекции по типам
     * Инициализируется один раз
     */
    private void fillSourceMap(List<Source> list) {
        for (OperationType type : OperationType.values()) {
            sourceMap.put(type, list.stream().filter(s -> s.getOperationType() == type).collect(Collectors.toList()));
        }
    }

    @Override
    public List<Source> getAll() {
        return treeSource;
    }

    @Override
    public Source get(int id) {
        return identityMap.get(id);
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
            removeFromCollections(source);
            return true;
        }
        return false;
    }

    @Override
    public List<Source> getList(OperationType operationType) {
        return sourceMap.get(operationType);
    }

    @Override
    public boolean add(Source object) {
        if (sourceDAO.add(object)) {
            addToCollections(object);
            return true;
        }
        return false;
    }

    // если понадабятся получить напрямую из БД
    public SourceDAO getSourceDAO() {
        return sourceDAO;
    }

    private void addToCollections(Source source) {
        identityMap.put(source.getId(), source);

        if (source.hasParent()) {
            if (!source.getParent().getChilds().contains(source)) { // если это ребенок и он раннее не был добавлен
                source.getParent().addChild(source);
            }
        } else { // если элемент корневой
            sourceMap.get(source.getOperationType()).add(source);
            treeSource.add(source);
        }
    }

    private void removeFromCollections(Source source) {
        identityMap.remove(source.getId(), source);
        if (source.hasParent()) { // если это дочерний `элемент
            source.getParent().removeChild(source);
        } else { // если элемент корневой
            sourceMap.get(source.getOperationType()).remove(source);
            treeSource.remove(source);
        }
    }
}
