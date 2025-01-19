package controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import model.Pair;
import model.Symptoms;

/*
 * @Mu Ye Liu - Jan 2025
 * 
 * Represents a controller class that acts as the intermediate between front end and backend
 */
public class Controller {
    
    // Stores the symptoms instance.
    private Symptoms symptoms;

    // Gets the instance of the symtptoms singleton class.
    public Controller() {
        symptoms = Symptoms.getInstance();
    }

    @GetMapping("/symptoms/{message}")
    public List<Pair<String, String>> getTop4Symptoms(@PathVariable String message) {
        return symptoms.getTop4Symptoms(message);
    }
}
