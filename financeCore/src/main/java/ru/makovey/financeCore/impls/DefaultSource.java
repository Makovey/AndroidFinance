package ru.makovey.financeCore.impls;

import java.util.List;

import ru.makovey.financeCore.abstracts.AbstractCompositeTree;
import ru.makovey.financeCore.interfaces.CompositeTree;
import ru.makovey.financeCore.interfaces.Source;
import ru.makovey.financeCore.objects.OperationType;

public class DefaultSource extends AbstractCompositeTree implements Source {

    public DefaultSource() {
    }

    public DefaultSource(String name) {
        super(name);
    }

    public DefaultSource(String name, int id) {
        super(name, id);
    }

    public DefaultSource(List<CompositeTree> childs) {
        super(childs);
    }

    public DefaultSource(String name, int id, List<CompositeTree> childs, CompositeTree parent) {
        super(name, id, childs, parent);
    }

    public DefaultSource(String name, ru.makovey.financeCore.objects.OperationType operationType) {
        super(name);
        operationType = operationType;
    }

    public DefaultSource(String name, int id, ru.makovey.financeCore.objects.OperationType operationType) {
        super(name, id);
        operationType = operationType;
    }

    private OperationType operationType;

    @Override
    public ru.makovey.financeCore.objects.OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(ru.makovey.financeCore.objects.OperationType operationType) {
        operationType = operationType;
    }

    @Override
    public void addChild(CompositeTree child) {
        //TODO убрать instanceof. Сделать корректную проверку
        /**
         * Ребенку сразу добавляем тип операции
         */
        if(child instanceof DefaultSource){
            ((DefaultSource) child).setOperationType(this.operationType);
        }
        super.addChild(child);
    }
}
