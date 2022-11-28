package com.secucom.spring.login.controllers;

import com.secucom.spring.login.models.ERole;
import com.secucom.spring.login.repository.RoleRepository;
import com.secucom.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.logging.Logger;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  private static final Logger LOG = Logger.getLogger(AuthController.class.getName());
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @GetMapping("/all")
  public String allAccess(Principal all) {


    String user = "NOM D'UTILISATEUR: " + userRepository.findByUsername(all.getName()).get().getUsername() + "  EMAIL:  "+
            userRepository.findByUsername(all.getName()).get().getEmail();
    LOG.info("Bienvenue, " + user);
    return "Bienvenue " +userRepository.findByUsername(all.getName()).get().getUsername();
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('COLLABORATEUR') or hasRole('ADMIN')")
  public String userAccess(Principal user) {
    LOG.info("Bienvenue, " + userRepository.findByUsername(user.getName()).get().getUsername() +
            roleRepository.findByName(ERole.ROLE_USER).get().getName());
    return "Bienvenue, " + userRepository.findByUsername(user.getName()).get().getUsername() +
            roleRepository.findByName(ERole.ROLE_USER).get().getName();
  }

  @GetMapping("/col")
  @PreAuthorize("hasRole('COLLABORATEUR')")
  public String collaborateurAccess() {
    LOG.info("Bienvenu collaborateur.");
    return "Bienvenu collaborateur.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess(Principal admin) {
    LOG.info("Bienvenue " + " "+ userRepository.findByUsername(admin.getName()).get().getUsername()  + " "+
            roleRepository.findByName(ERole.ROLE_ADMIN).get().getName());
    return "Bienvenue " + " "+ userRepository.findByUsername(admin.getName()).get().getUsername()  + " "+
            roleRepository.findByName(ERole.ROLE_ADMIN).get().getName();
  }
}
