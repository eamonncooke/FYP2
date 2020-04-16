/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.AuthUser;
import com.example.demo.model.DBUtil;
import com.example.demo.model.Player;
import com.example.demo.model.Testing;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author cooke
 */
@Service
public class ServiceForPlayer {
    
    public AuthUser getUserByEmail(String email) {
        //List<Player> list = repo.findAll();
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        AuthUser user = (AuthUser) em.createNamedQuery("AuthUser.findByEmail")
                .setParameter("email", email)
                .getSingleResult();
        if(user == null)
            throw new UsernameNotFoundException("No user found");
        
        return user;
    }
    public Player getPlayersByUserId(AuthUser user) {

        //List<Player> list = repo.findAll();
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Player> list = em.createNamedQuery("Player.findAll").getResultList();
        Player newP = new Player();
        for(Player p : list){
            if(p.getAuthUserId().getAuthUserId() == user.getAuthUserId())
                newP = p;
        }
        if(newP == null)
            throw new UsernameNotFoundException("No player found");
        
        return newP;
    }
    
    public List<Testing> getAllTest() {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Testing> list = em.createNamedQuery("Testing.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No Past Tests Found");
        
        return list;
    }
    public int getNewTestingNum()
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Testing test = (Testing) em.createNamedQuery("Testing.findLastTestNumber")
                .setMaxResults(1).getSingleResult();      
        int listingNo = test.getTestId() + 1;
        return listingNo;
    }
    
    public void addNewTest(Testing test)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(test);
        trans.commit();
    }
    
    public void addStravaToPlayer(Player player)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.merge(player);
        trans.commit();
    }
}
