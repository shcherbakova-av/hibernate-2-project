package org.example.service;

import org.example.dao.*;
import org.example.dto.CreateRentalRequest;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

public class RentalService {

    private final SessionFactory sessionFactory;
    private final RentalDAO rentalDAO;

    private final InventoryDAO inventoryDAO;
    private final CustomerDAO customerDAO;
    private final StaffDAO staffDAO;
    private final PaymentDAO paymentDAO;

    public RentalService(SessionFactory sessionFactory,
                         RentalDAO rentalDAO, InventoryDAO inventoryDAO, CustomerDAO customerDAO, StaffDAO staffDAO, PaymentDAO paymentDAO) {
        this.sessionFactory = sessionFactory;
        this.rentalDAO = rentalDAO;
        this.inventoryDAO = inventoryDAO;
        this.customerDAO = customerDAO;
        this.staffDAO = staffDAO;
        this.paymentDAO = paymentDAO;
    }

    public Integer rentFilm(CreateRentalRequest request) {

        if (request == null
                || request.customerId() == null
                || request.inventoryId() == null
                || request.staffId() == null
                || request.amount() == null
                || request.amount().signum() <= 0) {
            throw new IllegalArgumentException("Incorrect rental data");
        }

        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();

            Customer customer = customerDAO.getById(request.customerId());
            Inventory inventory = inventoryDAO.getByIdForUpdate(request.inventoryId());
            Staff staff = staffDAO.getById(request.staffId());

            if (customer == null) {
                throw new IllegalArgumentException("Customer not found");
            }

            if (inventory == null) {
                throw new IllegalArgumentException("inventory film not found");
            }

            if (staff == null) {
                throw new IllegalArgumentException("staff not found");
            }

            if (!staff.getStore().getId().equals(inventory.getStore().getId())) {
                throw new IllegalStateException(
                        "staff not work here"
                );
            }

            Rental lastRental = rentalDAO.findLatestByInventoryId(request.inventoryId());

            if (lastRental != null && lastRental.getReturnDate() == null) {
                throw new IllegalStateException(
                        "A copy of the film is already out on loan"
                );
            }

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);

            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(request.amount());

            paymentDAO.save(payment);

            transaction.commit();

            return rental.getId();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void returnFilm(int rentalId, short customerId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Rental rental = rentalDAO.getById(rentalId);

            if (rental == null) {
                throw new IllegalArgumentException(
                        "Rental not found " + rentalId
                );
            }

            if (!rental.getCustomer().getId().equals(customerId)) {
                throw new IllegalArgumentException(
                        "The rental is not the same person as the customer: " + customerId
                );
            }

            if (rental.getReturnDate() != null) {
                throw new IllegalStateException(
                        "Film has been returned"
                );
            }

            rental.setReturnDate(LocalDateTime.now());

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
