package com.bpt.tutorials.jpatesttutorial.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 * @author peter
 */
public class SetupTestDBRule implements TestRule{
    private final Object testClass;
    private final ArrayList<EntityManager> entityManagers;
    public SetupTestDBRule(Object testClass) {
        this.testClass = testClass;
        ArrayList<Field> fields = findFieldsRequestingEntityManager();
        entityManagers = createAndInjectEntityManagers(fields);
        
    }
    
    public Statement apply(Statement base, Description description) {
        return new SetupTestDBStatement(base, entityManagers);
    }

    private ArrayList<Field> findFieldsRequestingEntityManager() {
        Field[] declaredFields = testClass.getClass().getDeclaredFields();
        ArrayList<Field> annotatedFields = new ArrayList<Field>();
        if(declaredFields == null)
            return annotatedFields;
        
        Boolean faultFound = false;
        for(Field field : declaredFields) {
            PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
            if(annotation == null)
                continue;
            final String unitName = annotation.unitName();
            if(unitName.isEmpty()) {
                System.err.println("Field ["+ field.getName() +"] annotated with PersistenceContext must specify unitName");
                faultFound = true;
            } 
            
            annotatedFields.add(field);
            System.out.println("Field ["+ field.getName() +"] requests PU ["+unitName+"]");
                
        }
        
        if(faultFound)
            throw new RuntimeException("PersistenceContext annotated field(s) found with errors");
        
        return annotatedFields;
    }

        private ArrayList<EntityManager> createAndInjectEntityManagers(ArrayList<Field> fields) {
            ArrayList<EntityManager> entityManagers = new ArrayList<EntityManager>();
            for(Field field : fields) {
                try {
                    PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
                    EntityManager entityManager = Persistence.createEntityManagerFactory(annotation.unitName()).createEntityManager();
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(testClass, entityManager);
                    field.setAccessible(accessible);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            return entityManagers;
        }
    
}
