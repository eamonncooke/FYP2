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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author cooke
 */
@Service
public class ServiceForCoach {
    //@Autowired
    //private PlayerRepository repo;
    
    public List<Player> getAllPlayers() {

        //List<Player> list = repo.findAll();
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Player> list = em.createNamedQuery("Player.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No Players found");
        
        return list;
    }
    
    public List<Testing> getAllTest() {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Testing> list = em.createNamedQuery("Testing.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No Past Tests Found");
        
        return list;
    }
    
    public Player getPlayer(AuthUser user){
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Player player = em.find(Player.class, user.getAuthUserId());
        System.out.println(player.getAuthUserId().getFirstName());
        if(player == null)
            throw new UsernameNotFoundException("No player found");
        
        return player;
    }
}
