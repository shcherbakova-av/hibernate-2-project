
package org.example;

import org.example.config.HibernateUtil;
import org.example.dao.*;
import org.example.dto.CreateCustomerRequest;
import org.example.dto.CreateFilmRequest;
import org.example.dto.CreateRentalRequest;
import org.example.entity.Rating;
import org.example.service.CustomerService;
import org.example.service.FilmService;
import org.example.service.RentalService;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory =
                HibernateUtil.getSessionFactory();


        try {
            //        6. Пункт: Сохраняем нового клиента
//            AddressDAO addressDAO = new AddressDAO(sessionFactory);
//            CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
//            CityDAO cityDAO = new CityDAO(sessionFactory);
//            StoreDAO storeDAO = new StoreDAO(sessionFactory);
//
//            CustomerService customerService = new CustomerService(
//                    sessionFactory,
//                    addressDAO,
//                    customerDAO,
//                    cityDAO,
//                    storeDAO
//            );
//
//            CreateCustomerRequest request = new CreateCustomerRequest(
//                    "Anna",
//                    "Shcher",
//                    "anna.test@example.com",
//                    "Test Street, 10",
//                    null,
//                    "Central District",
//                    "050000",
//                    "77001234567",
//                    (short) 1,
//                    (byte) 1,
//                    true
//            );
//
//            Short customerId = customerService.createCustomer(request);
//
//            System.out.println("Создан покупатель с ID: " + customerId);

            //        7. Пункт: Возвращаем аренду
//
//            RentalDAO rentalDAO = new RentalDAO(sessionFactory);
//
//            RentalService rentalService = new RentalService(sessionFactory, rentalDAO);
//
//            rentalService.returnFilm(15867, (short) 505);
//
//            System.out.println("Film return");


//            8. Пункт: Создаем аренду

//            RentalDAO rentalDAO = new RentalDAO(sessionFactory);
//            InventoryDAO inventoryDAO = new InventoryDAO(sessionFactory);
//            CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
//            StaffDAO staffDAO = new StaffDAO(sessionFactory);
//            PaymentDAO paymentDAO = new PaymentDAO(sessionFactory);
//
//            RentalService rentalService = new RentalService(
//                    sessionFactory,
//                    rentalDAO,
//                    inventoryDAO,
//                    customerDAO,
//                    staffDAO,
//                    paymentDAO
//            );
//
//            CreateRentalRequest request = new CreateRentalRequest(
//                    (short) 505,
//                    944,
//                    (byte) 1,
//                    new BigDecimal("2.99")
//            );
//
//            Integer rentalId = rentalService.rentFilm(request);
//
//            System.out.println("Rental created, Id: " + rentalId);

//            9. Создаем новый фильм и добавляем в коллекцию аренды

            FilmDAO filmDAO = new FilmDAO(sessionFactory);
            LanguageDAO languageDAO = new LanguageDAO(sessionFactory);
            ActorDAO actorDAO = new ActorDAO(sessionFactory);
            CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);
            StoreDAO storeDAO = new StoreDAO(sessionFactory);
            InventoryDAO inventoryDAO = new InventoryDAO(sessionFactory);

            FilmService filmService = new FilmService(
                    sessionFactory,
                    filmDAO,
                    languageDAO,
                    actorDAO,
                    categoryDAO,
                    storeDAO,
                    inventoryDAO
            );

            CreateFilmRequest request = new CreateFilmRequest(
                    "JAVA ADVENTURES",
                    "A developer explores the world of Java.",
                    Year.of(2026),
                    (byte) 1,
                    null,
                    (byte) 3,
                    new BigDecimal("2.99"),
                    (short) 120,
                    new BigDecimal("19.99"),
                    Rating.PG13,
                    "Trailers",
                    Set.of((short) 1, (short) 2),
                    Set.of((byte) 1),
                    (byte) 1
            );

            Short filmId = filmService.createFilm(request);

            System.out.println("Создан новый фильм, ID: " + filmId);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
