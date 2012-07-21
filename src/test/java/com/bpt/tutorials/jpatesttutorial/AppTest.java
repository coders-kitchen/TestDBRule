package com.bpt.tutorials.jpatesttutorial;

import com.bpt.tutorials.jpatesttutorial.util.SetupTestDBRule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest {
    @Rule
    public SetupTestDBRule setupTestDBRule = new SetupTestDBRule(this);
    
    @PersistenceContext(unitName="RuleTutorialPU")
    EntityManager first;
    
    @PersistenceContext(unitName="SecondRuleTutorialPU")
    EntityManager second;
    
    @Test
    public void out() {
        assertTrue(true);
    }
}
