package com.secucom.spring.login.controllers;

import com.secucom.spring.login.security.services.UserService;
import com.secucom.spring.login.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

public class UserController {
    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());
    @Autowired
    private UserService usersCrud;


    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/afficher")
    public List<User> AfficherUsers(){
        LOG.info("usersCrud.Afficher()");
        return usersCrud.Afficher();
    }

    // µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ   MODIFIER
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier"})
    public String ModierUser(@RequestBody User users){

        usersCrud.Modifier(users);
        LOG.info("Modification reussie avec succès");
        return "Modification reussie avec succès";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{id_users}")
    public String Supprimer(@PathVariable("id_users") Long id_users){
        usersCrud.Supprimer(id_users);
        LOG.info("Suppression reussie");
        return "Suppression reussie";
    }

}
