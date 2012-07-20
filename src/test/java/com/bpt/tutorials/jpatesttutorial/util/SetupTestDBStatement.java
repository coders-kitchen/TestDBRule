package com.bpt.tutorials.jpatesttutorial.util;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.junit.runners.model.Statement;

/**
 *
 * @author peter
 */
public class SetupTestDBStatement extends Statement {
    private final Statement surrounded;
    private final ArrayList<EntityManager> entityManagers;

    public SetupTestDBStatement(Statement surrounded, ArrayList<EntityManager> entityManagers) {
        this.surrounded = surrounded;
        this.entityManagers = entityManagers;
    }
    
    @Override
    public void evaluate() throws Throwable {
        try {
            for(EntityManager em : entityManagers)
                em.getTransaction().begin();
            surrounded.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Closing EntityManagers");
            for(EntityManager em : entityManagers)
                try {
                    em.getTransaction().rollback();
                    em.close();
                    
                } catch(Exception ex) {
                    ex.printStackTrace();
                }               
        }
    }   
}
