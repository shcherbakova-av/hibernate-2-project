package org.example.dao;

import org.example.entity.Inventory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

public class InventoryDAO extends GenericDAO<Inventory> {

    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public Inventory getByIdForUpdate(Integer inventoryId) {
        return getCurrentSession().get(
                Inventory.class,
                inventoryId,
                LockMode.PESSIMISTIC_WRITE
        );
    }
}
