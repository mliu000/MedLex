package muye1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import muye1.model.Pair;
import muye1.model.Symptoms;

/*
 * @Mu Ye Liu - Jan 2025
 * 
 * Represents a controller class that acts as the intermediate between front end and backend
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
    
    // Stores the symptoms instance.
    private Symptoms symptoms;

    // Gets the instance of the symtptoms singleton class.
    public Controller() {
        symptoms = Symptoms.getInstance();
    }

    @PostMapping("/symptoms")
    // Gateway method to have the back end process information and return it to the front end
    public List<Pair<String, String>> getTop4Symptoms(@RequestBody String message) {
        return symptoms.getTop4Symptoms(message);
    }
}
