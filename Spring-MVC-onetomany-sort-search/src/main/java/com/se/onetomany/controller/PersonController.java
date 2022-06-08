/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.onetomany.controller;

/**
 *
 * @author TriPham
 */
import com.se.onetomany.entity.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.se.onetomany.service.PersonService;
import com.se.onetomany.entity.Person;
import com.se.onetomany.sortutil.SortUtils;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    // need to inject our customer service
    private PersonService personService;

    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") Person thePerson) {
        // save the customer using our service
        List<CreditCard> creditCards = null;
        if (thePerson.getId() != 0) {
            creditCards = personService.getCreditCards(thePerson.getId());
        }

        thePerson.setCreditCards(creditCards);
        personService.savePerson(thePerson);
        return "redirect:/person/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("personId") int theId,
            Model theModel) {
        // get the customer from our service
        Person thePerson = personService.getPerson(theId);
        // set customer as a model attribute to pre-populate the form
        theModel.addAttribute("person", thePerson);

        // send over to our form		
        return "person-form";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        // create model attribute to bind form data
        Person thePerson = new Person();
        theModel.addAttribute("person", thePerson);
        //theModel.addAttribute("update", false);
        return "person-form";
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam("personId") int theId) {

        // delete the customer
        personService.deletePerson(theId);

        return "redirect:/person/list";
    }

    @GetMapping("/list")
    public String listPersons(Model theModel, @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String lastSortField, @RequestParam(required = false) String sortUD) {
        // get persons from the service
        List<Person> thePersons = null;
        // check for sort field
        //int sortF= Integer.parseInt(sortField);
        int theSortUD=SortUtils.UP;
        int theSortField= SortUtils.LAST_NAME;
        //int lsortField= Integer.parseInt(lastSortField);
        
        if (sortField != null && lastSortField!=null && sortUD!=null) 
        {
           if(Integer.parseInt(sortUD)==SortUtils.UP && sortField.equals(lastSortField))
               theSortUD=SortUtils.DOWN;
           theSortField = Integer.parseInt(sortField);
        }
        
        theModel.addAttribute("sortField",theSortField);
        theModel.addAttribute("sortUD",String.valueOf(theSortUD));
        thePersons = personService.getPersons(theSortField, String.valueOf(theSortUD));
        theModel.addAttribute("persons",thePersons);
      return "list-persons";
   }

    @GetMapping("/search")
    public String searchPersons(@RequestParam("theSearchName") String theSearchName,
            Model theModel) {
        // search customers from the service
        List<Person> thePersons = personService.searchPersons(theSearchName);

        // add the customers to the model
        theModel.addAttribute("persons", thePersons);
        return "search-persons";
    }
}
