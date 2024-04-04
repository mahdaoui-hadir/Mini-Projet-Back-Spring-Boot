package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.serivce.ActualiteesService;
import com.springboot.MiniProject.serivce.DemandeService;
import com.springboot.MiniProject.serivce.EnseignantService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issatso/enseignant")
public class EnseignantController {

    @Autowired
    private UserService service;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private ActualiteesService actualiteesService;

    //cette PAGE est accessible par les enseignants seulement
    @GetMapping("/welcome/ens")
    @PreAuthorize("hasAuthority('ROLE_ENS')")
    public String welcomeEns() {
        return "Welcome ens";
    }
    @PutMapping("/update/NumTel")
    public EnseignantDTO updateEnseignantInfo(@RequestBody EnseignantDTO enseignantDTO){
        return enseignantService.updateEtudiantInfo(enseignantDTO);
    }

    /////Demande ////
    @GetMapping("/Demande/getAllDemande")
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }
    @PostMapping("/Demande/AddDemande")
    public ResponseEntity<DemandeDTO> createDemande(@RequestBody DemandeDTO demandeDTO) {
        DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);
        return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
    }
    @PutMapping("/Demande/updateDemande/{id}")
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long id, @RequestBody DemandeDTO demandeDTO) {
        DemandeDTO updatedDemande = demandeService.updateDemande(id, demandeDTO);
        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }



    ///////////Actualitees //////////////
    @GetMapping("/Actualitee/{target}")
    public ResponseEntity<List<Actualitees>> getActualiteeEnseignant(@PathVariable String target ) {
        List<Actualitees> news =actualiteesService.getNewsByTargetAudiance(target);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }


    ////////////// Matieres ////////////////
    @GetMapping("/Matiere/AllMatiere/{id}")
    public List<MatiereEnsDTO> getMatieresEnseigneesParEnseignant(@PathVariable int id ) {
        List<MatiereEnsDTO> mesMat = enseignantService.getMatieresEnseigneesParEnseignant(id);
        return new ResponseEntity<>(mesMat, HttpStatus.OK).getBody();
    }

    @GetMapping("/Matiere/Allgroupes/{id}")
    public List<GroupEnsDTO> getGroupesEnseignesParEnseignant(@PathVariable int id ) {
        List<GroupEnsDTO> mesGrp = enseignantService.getGroupesEnseignesParEnseignant(id);
        return new ResponseEntity<>(mesGrp, HttpStatus.OK).getBody();
    }


}