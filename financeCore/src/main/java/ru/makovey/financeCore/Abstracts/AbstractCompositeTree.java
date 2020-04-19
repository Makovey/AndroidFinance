package ru.makovey.financeCore.Abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.makovey.financeCore.interfaces.CompositeTree;

public abstract class AbstractCompositeTree implements CompositeTree {

    public AbstractCompositeTree() {
    }

    public AbstractCompositeTree(String name) {
        this.name = name;
    }

    public AbstractCompositeTree(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public AbstractCompositeTree(List<CompositeTree> childs) {
        this.childs = childs;
    }

    public AbstractCompositeTree(String name, int id, List<CompositeTree> childs, CompositeTree parent) {
        this.name = name;
        this.id = id;
        this.childs = childs;
        this.parent = parent;
    }


    private String name;

    private int id;

    private List<CompositeTree> childs = new ArrayList<>();

    private CompositeTree parent;


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void addChild(CompositeTree child) {
        parent.setParent(this);
        childs.add(child);
    }

    @Override
    public void removeChild(CompositeTree child) {
        childs.remove(child);
    }

    @Override
    public List<CompositeTree> getChilds() {
        return childs;
    }

    @Override
    public CompositeTree getChildById(int id) {
        for (CompositeTree child : childs) {
            if (child.getId() == id) {
                return child;
            }
        }
        return null;
    }

    @Override
    public CompositeTree getParent() {
        return parent;
    }

    @Override
    public void setParent(CompositeTree parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCompositeTree that = (AbstractCompositeTree) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
