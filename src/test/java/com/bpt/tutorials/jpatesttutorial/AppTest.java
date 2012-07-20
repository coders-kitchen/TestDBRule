package com.bpt.tutorials.jpatesttutorial;

import com.bpt.tutorials.jpatesttutorial.util.SetupTestDBRule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Rule;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest {
    @Rule
    public SetupTestDBRule setupTestDBRule = new SetupTestDBRule(this);
    
    @PersistenceContext(unitName="TutorialPU")
    EntityManager first;
    
    @PersistenceContext(unitName="TutorialPU")
    EntityManager second;
    
    @Test
    public void out() {
        System.out.println("STRING");
    }
}
