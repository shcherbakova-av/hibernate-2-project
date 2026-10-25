package org.example.service;

import org.example.dao.*;
import org.example.dto.CreateCustomerRequest;
import org.example.dto.CreateFilmRequest;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FilmService {

    private final SessionFactory sessionFactory;

    private final FilmDAO filmDAO;
    private final LanguageDAO languageDAO;
    private final ActorDAO actorDAO;
    private final CategoryDAO categoryDAO;
    private final StoreDAO storeDAO;
    private final InventoryDAO inventoryDAO;

    public FilmService(
            SessionFactory sessionFactory,
            FilmDAO filmDAO,
            LanguageDAO languageDAO,
            ActorDAO actorDAO,
            CategoryDAO categoryDAO,
            StoreDAO storeDAO,
            InventoryDAO inventoryDAO
    ) {
        this.sessionFactory = sessionFactory;
        this.filmDAO = filmDAO;
        this.languageDAO = languageDAO;
        this.actorDAO = actorDAO;
        this.categoryDAO = categoryDAO;
        this.storeDAO = storeDAO;
        this.inventoryDAO = inventoryDAO;
    }


    public Short createFilm(CreateFilmRequest request) {

        if (request == null
                || request.title() == null
                || request.title().isBlank()
                || request.languageId() == null
                || request.storeId() == null
                || request.rentalDuration() == null
                || request.rentalRate() == null
                || request.replacementCost() == null
                || request.rentalRate().signum() < 0
                || request.replacementCost().signum() < 0) {
            throw new IllegalArgumentException("Invalid film data");
        }

        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();

            Language language = languageDAO.getById(request.languageId());
            Store store = storeDAO.getById(request.storeId());

            if (language == null) {
                throw new IllegalArgumentException("Language not found");
            }

            if (store == null) {
                throw new IllegalArgumentException("Store not found");
            }

            Film film = new Film();

            film.setTitle(request.title());
            film.setDescription(request.description());
            film.setYear(request.year());
            film.setLanguage(language);
            film.setRentalDuration(request.rentalDuration());
            film.setRentalRate(request.rentalRate());
            film.setLength(request.length());
            film.setReplacementCost(request.replacementCost());
            film.setRating(request.rating());
            film.setSpecialFeatures(request.specialFeatures());

            if (request.originalLanguageId() != null) {
                Language originalLanguage =
                        languageDAO.getById(request.originalLanguageId());

                if (originalLanguage == null) {
                    throw new IllegalArgumentException(
                            "Original language not found"
                    );
                }

                film.setOriginalLanguage(originalLanguage);
            }

            if (request.actorIds() != null) {
                for (Short actorId : request.actorIds()) {
                    Actor actor = actorDAO.getById(actorId);

                    if (actor == null) {
                        throw new IllegalArgumentException(
                                "Actor not found: " + actorId
                        );
                    }

                    film.addActor(actor);
                }
            }

            if (request.categoryIds() != null) {
                for (Byte categoryId : request.categoryIds()) {
                    Category category = categoryDAO.getById(categoryId);

                    if (category == null) {
                        throw new IllegalArgumentException(
                                "Category not found: " + categoryId
                        );
                    }

                    film.addCategory(category);
                }
            }

            filmDAO.save(film);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);

            inventoryDAO.save(inventory);

            transaction.commit();

            return film.getId();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
