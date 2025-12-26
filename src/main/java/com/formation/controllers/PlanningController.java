package com.formation.controllers;

import com.formation.dto.*;
import com.formation.models.*;
import com.formation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for planning generation and optimization
 */
@RestController
@RequestMapping("/api/planning")
@CrossOrigin(origins = "*") // Allow CORS for frontend integration
public class PlanningController {
    
    @Autowired
    private PlanningOptimisationService optimisationService;
    
    @Autowired
    private ContrainteService contrainteService;
    
    /**
     * Generate optimized planning
     * POST /api/planning/generate
     */
    @PostMapping("/generate")
    public ResponseEntity<PlanningResponse> genererPlanning(@RequestBody PlanningRequest request) {
        try {
            // Validate input
            if (request.getCours() == null || request.getCours().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("La liste des cours est vide ou manquante"));
            }
            
            if (request.getFormateurs() == null || request.getFormateurs().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("La liste des formateurs est vide ou manquante"));
            }
            
            if (request.getSalles() == null || request.getSalles().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("La liste des salles est vide ou manquante"));
            }
            
            // Set custom time slots if provided
            if (request.getCreneauxPersonnalises() != null && !request.getCreneauxPersonnalises().isEmpty()) {
                optimisationService.setCreneauxDisponibles(request.getCreneauxPersonnalises());
            }
            
            // Generate planning
            PlanningOptimisationService.ResultatOptimisation resultat = 
                optimisationService.genererPlanningOptimise(
                    request.getCours(),
                    request.getFormateurs(),
                    request.getSalles()
                );
            
            // Build response
            PlanningResponse response = new PlanningResponse();
            response.setPlanning(resultat.getPlanning());
            response.setCoursNonPlanifies(resultat.getCoursNonPlanifies());
            response.setConflits(resultat.getConflits());
            response.setStatistiques(resultat.getStatistiques());
            response.setTempsExecution(resultat.getTempsExecution());
            response.setStatut(resultat.getStatut());
            
            // Set message based on status
            switch (resultat.getStatut()) {
                case "SUCCES":
                    response.setMessage("Planning généré avec succès sans conflits");
                    break;
                case "PARTIEL":
                    response.setMessage("Planning généré partiellement avec " + 
                                      resultat.getCoursNonPlanifies().size() + 
                                      " cours non planifiés et " + 
                                      resultat.getConflits().size() + " conflits");
                    break;
                case "ECHEC":
                    response.setMessage("Échec de la génération du planning");
                    break;
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Erreur lors de la génération: " + e.getMessage()));
        }
    }
    
    /**
     * Verify constraints for a specific session
     * POST /api/planning/verify
     */
    @PostMapping("/verify")
    public ResponseEntity<List<ContrainteService.Conflit>> verifierContraintes(
            @RequestBody SessionPlanifiee session,
            @RequestParam(required = false) boolean includeContext) {
        try {
            List<ContrainteService.Conflit> conflits;
            
            if (includeContext) {
                // Check with context (would need current planning from DB)
                conflits = contrainteService.verifierContraintesSimples(session);
            } else {
                conflits = contrainteService.verifierContraintesSimples(session);
            }
            
            return ResponseEntity.ok(conflits);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get available time slots
     * GET /api/planning/timeslots
     */
    @GetMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> getCreneauxDisponibles() {
        return ResponseEntity.ok(optimisationService.getCreneauxDisponibles());
    }
    
    /**
     * Detect all conflicts in a given planning
     * POST /api/planning/conflicts
     */
    @PostMapping("/conflicts")
    public ResponseEntity<List<ContrainteService.Conflit>> detecterConflits(
            @RequestBody List<SessionPlanifiee> planning) {
        try {
            List<ContrainteService.Conflit> conflits = 
                contrainteService.detecterTousLesConflits(planning);
            return ResponseEntity.ok(conflits);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Calculate quality score for a session
     * POST /api/planning/score
     */
    @PostMapping("/score")
    public ResponseEntity<Double> calculerScore(@RequestBody SessionPlanifiee session) {
        try {
            double score = contrainteService.calculerScore(session);
            return ResponseEntity.ok(score);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/planning/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Planning Optimisation Service is running");
    }
    
    /**
     * Helper method to create error response
     */
    private PlanningResponse createErrorResponse(String message) {
        PlanningResponse response = new PlanningResponse();
        response.setStatut("ERREUR");
        response.setMessage(message);
        return response;
    }
}
