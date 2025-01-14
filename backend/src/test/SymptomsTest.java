package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Pair;
import model.Symptoms;

public class SymptomsTest {

    // Instance of the symptoms class
    public Symptoms symptoms;

    // Initializes the symptoms class, if it hasn't been initialized yet.
    @Before
    public void construct() {
        symptoms = Symptoms.getInstance();
    }

    // Tests the constructor/initializer
    @Test
    public void constructorTest() {

        // Check the size of the hashmaps
        assertEquals(49, symptoms.getSympDef().size());
        assertEquals(49, symptoms.getSympWords().size());

        // Checks the inputs of the sympdef
        List<String> listCW = symptoms.getSympWords().get("Fever");
        List<String> listCWdoesntExist = symptoms.getSympWords().get("Fevers");
        assertFalse(listCW == null);
        assertNull(listCWdoesntExist);

        // Checks the values of one entry
        assertEquals(7, listCW.size());
        assertEquals("hot", listCW.get(0));
        assertEquals("chills", listCW.get(6));
    }

    // Tests the method that returns the top 3 symptoms with case examples
    @Test 
    public void top3SymptomsTest() {
        // Case 1: The input string is completely invalid (wordset is empty)
        List<Pair<String, String>> list1 = symptoms.getTop4Symptoms("34234@$ 25");
        assertNull(list1);

        // Case 2: the input string is not completely invalid, but couldn't find any symptoms
        List<Pair<String, String>> list2 = symptoms.getTop4Symptoms("stupid 24");
        assertNull(list2);

        // Case 3: Only one word (list is full size)
        List<Pair<String, String>> list3 = symptoms.getTop4Symptoms("hot");
        assertFalse(list3 == null);
        assertEquals(4, list3.size());
        List<Pair<String, String>> expected3 = new ArrayList<>();
        expected3.add(new Pair<String,String>("Heartburn", "A burning pain in the chest caused by acid reflux."));
        expected3.add(new Pair<String,String>("Burning Sensation", "A painful feeling of heat or irritation in an area."));
        expected3.add(new Pair<String,String>("Sweating", "The body’s release of moisture to regulate temperature."));
        expected3.add(new Pair<String,String>("Fever", "An elevated body temperature, often caused by infection or illness."));
        for (int i = 0; i < 4; i++) {
            assertEquals(expected3.get(i).getFirst(), list3.get(i).getFirst());
            assertEquals(expected3.get(i).getSecond(), list3.get(i).getSecond());
        }

        // Case 4: List is not full size
        List<Pair<String, String>> list4 = symptoms.getTop4Symptoms("cracked");
        assertFalse(list4 == null);
        assertEquals(2, list4.size());
        List<Pair<String, String>> expected4 = new ArrayList<>();
        expected4.add(new Pair<String,String>("Dry Mouth", "A lack of saliva causing dryness or stickiness in the mouth."));
        expected4.add(new Pair<String,String>("Dehydration", "A condition caused by insufficient water in the body."));
        for (int i = 0; i < 2; i++) {
            assertEquals(expected4.get(i).getFirst(), list4.get(i).getFirst());
            assertEquals(expected4.get(i).getSecond(), list4.get(i).getSecond());
        }

        // Case 5: various relevant words
        List<Pair<String, String>> list5 = symptoms.getTop4Symptoms("hot cracked dizzy sweating");
        assertFalse(list5 == null);
        assertEquals(4, list5.size());
        List<Pair<String, String>> expected5 = new ArrayList<>();
        expected5.add(new Pair<String,String>("Dehydration", "A condition caused by insufficient water in the body."));
        expected5.add(new Pair<String,String>("Fever", "An elevated body temperature, often caused by infection or illness."));
        expected5.add(new Pair<String,String>("Dry Mouth", "A lack of saliva causing dryness or stickiness in the mouth."));
        expected5.add(new Pair<String,String>("Fainting", "Sudden loss of consciousness, usually temporary."));
        for (int i = 0; i < 4; i++) {
            assertEquals(expected5.get(i).getFirst(), list5.get(i).getFirst());
            assertEquals(expected5.get(i).getSecond(), list5.get(i).getSecond());
        }
    }

}
