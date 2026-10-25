package org.example.dao;

import org.example.entity.City;
import org.hibernate.SessionFactory;

public class CityDAO extends GenericDAO<City> {

    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }
}
