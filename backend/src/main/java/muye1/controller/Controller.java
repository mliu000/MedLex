package muye1.controller;

import java.util.List;

import muye1.model.Pair;
import muye1.model.Symptoms;

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

    // Gateway method to have the back end process information and return it to the front end
    public List<Pair<String, String>> getTop4Symptoms(String message) {
        return symptoms.getTop4Symptoms(message);
    }
}
