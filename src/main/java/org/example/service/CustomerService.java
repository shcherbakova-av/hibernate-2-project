
package org.example.service;

import org.example.dao.*;
import org.example.dto.CreateCustomerRequest;
import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CustomerService {

    private final SessionFactory sessionFactory;
    private final AddressDAO addressDAO;
    private final CustomerDAO customerDAO;
    private final CityDAO cityDAO;
    private final StoreDAO storeDAO;

    public CustomerService(
            SessionFactory sessionFactory,
            AddressDAO addressDAO,
            CustomerDAO customerDAO,
            CityDAO cityDAO,
            StoreDAO storeDAO
    ) {
        this.sessionFactory = sessionFactory;
        this.addressDAO = addressDAO;
        this.customerDAO = customerDAO;
        this.cityDAO = cityDAO;
        this.storeDAO = storeDAO;
    }

    public Short createCustomer(CreateCustomerRequest request) {

        Transaction transaction = null;

        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();

            City city = cityDAO.getById(request.cityId());
            Store store = storeDAO.getById(request.storeId());

            if (city == null) {
                throw new IllegalArgumentException("City not found");
            }

            if (store == null) {
                throw new IllegalArgumentException("Store not found");
            }

            Address address = new Address();
            address.setAddress(request.address());
            address.setAddress2(request.address2());
            address.setDistrict(request.district());
            address.setPostalCode(request.postalCode());
            address.setPhone(request.phone());
            address.setCity(city);

            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setFirstName(request.firstName());
            customer.setLastName(request.lastName());
            customer.setEmail(request.email());
            customer.setActive(request.active());
            customer.setAddress(address);
            customer.setStore(store);

            customerDAO.save(customer);

            transaction.commit();

            return customer.getId();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

