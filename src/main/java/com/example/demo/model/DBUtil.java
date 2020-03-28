
package com.example.demo.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DBUtil {
    private static final EntityManagerFactory Emf = 
            Persistence.createEntityManagerFactory("trackerPU");
    
    public static EntityManagerFactory getEmf(){return Emf;}
}
