package muye1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @GetMapping("/symptoms/{message}")
    // Gateway method to have the back end process information and return it to the front end
    public List<Pair<String, String>> getTop4Symptoms(@PathVariable String message) {
        // Decode the message first
        try {
            String decodedMessage = URLDecoder.decode(message, "UTF-8");
            System.out.println("Reveived mesaage");
            return symptoms.getTop4Symptoms(decodedMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
