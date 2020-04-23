package ru.makovey.financeCore;

import ru.makovey.financeCore.dao.impls.StorageDAOImpl;

public class Start {
    public static void main(String[] args) {
        new StorageDAOImpl().getAll();
    }
}
