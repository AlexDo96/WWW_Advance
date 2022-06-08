/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.onetomany.dao;

import com.se.onetomany.entity.CreditCard;
import com.se.onetomany.entity.Person;
import com.se.onetomany.sortutil.SortUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TriPham
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Person getPerson(int thePersonId) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        // now retrieve/read from database using the primary key
        Person thePerson = currentSession.get(Person.class, thePersonId);
        return thePerson;
    }

    @Transactional
    @Override
    public List<Person> getPersons(int theSortField, String updown) {
        Session currentSession = sessionFactory.getCurrentSession();

        String theFieldName = null;

        switch (theSortField) {
            case SortUtils.FIRST_NAME:
                theFieldName = "firstName";
                break;
            case SortUtils.LAST_NAME:
                theFieldName = "lastName";
                break;
            case SortUtils.MONEY:    
                theFieldName = "money";
                break;
            default:
                // if nothing matches the default to sort by lastName
                theFieldName = "lastName";
        }
        Query<Person> theQuery = null;
        String queryString="";
        if ( Integer.parseInt(updown)==SortUtils.UP) 
            queryString = "from Person order by " + theFieldName + " ASC";
        else 
            queryString = "from Person order by " + theFieldName + " DESC";
           
        theQuery = currentSession.createQuery(queryString, Person.class);
        
        // execute query and get result list}
        List<Person> persons = theQuery.getResultList();
        return persons;
    }

    @Override
    public void savePerson(Person thePerson) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        // save/upate the customer ... finally
        currentSession.saveOrUpdate(thePerson);
    }
//          @Override
//        public void updatePerson(Person thePerson)
//        {
//             // get current hibernate session
//            Session currentSession = sessionFactory.getCurrentSession();
//            // save/upate the customer ... finally
//            currentSession.update(thePerson);
//        }

    @Override
    public List<CreditCard> getCreditCards(int thePersonId) {
        //Session currentSession = sessionFactory.getCurrentSession();
        Person thePerson = getPerson(thePersonId);
        if (thePerson != null) {
            List<CreditCard> creditCards = new ArrayList<>();
            if (thePerson.getCreditCards() != null) {
                creditCards = thePerson.getCreditCards();
            }
            return creditCards;
        }
        return null;
    }

    @Override
    public CreditCard getCreditCard(int personId, int creditCardId) {
        Person thePerson = getPerson(personId);
        if (thePerson != null) {
            return thePerson.getCreditCard(creditCardId);
        } else {
            return null;
        }
    }

    @Override
    public void deletePerson(int thePersonId) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        Person tempPerson = currentSession.get(Person.class, thePersonId);
        currentSession.delete(tempPerson);

// delete object with primary key
//            Query theQuery = 
//                            currentSession.createQuery("delete from Person where id=:personId");
//            theQuery.setParameter("personId", thePersonId);
//            theQuery.executeUpdate();	
    }

//    
    @Override
    public List<Person> searchPersons(String theSearchName) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery = null;

        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery = currentSession.createQuery("from Person where lower(firstName) like :theName or lower(lastName) like :theName", Person.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            // theSearchName is empty ... so just get all customers
            theQuery = currentSession.createQuery("from Person", Person.class);
        }

        // execute query and get result list
        List<Person> persons = theQuery.getResultList();

        // return the results        
        return persons;
    }
}
