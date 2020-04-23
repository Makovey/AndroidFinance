package ru.makovey.financeCore.util;

import java.util.List;

import ru.makovey.financeCore.interfaces.CompositeTree;

public class TreeBuilder<T extends CompositeTree> {

    public void addToTree(int parentId, T newNode, List<T> storageList) {
        if (parentId != 0) {
            for (T currentNode : storageList) {
                if (currentNode.getId() == parentId) currentNode.addChild(newNode);
                else {
                    CompositeTree tree = recursiveSearch(parentId, currentNode);
                    if (tree != null) tree.addChild(newNode);
                }
            }
        } else {
            storageList.add(newNode);
        }
    }

    private CompositeTree recursiveSearch(int parentId, CompositeTree child) {
        for (CompositeTree tree : child.getChilds()) {
            if (tree.getId() == parentId) return tree;
            else if (tree.hasChilds()) {
                recursiveSearch(parentId, tree);
            }
        }
        return null;
    }
}
