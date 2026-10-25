package org.example.dao;

import org.example.entity.Rental;
import org.hibernate.SessionFactory;

public class RentalDAO extends GenericDAO<Rental> {

    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental findLatestByInventoryId(Integer inventoryId) {

        return getCurrentSession()
                .createQuery(
                        """
                        SELECT r FROM Rental r
                        WHERE r.inventory.id = :inventoryId
                        ORDER BY r.rentalDate DESC, r.id DESC
                        """,
                        Rental.class
                )
                .setParameter("inventoryId", inventoryId)
                .setMaxResults(1)
                .uniqueResult();
    }
}
