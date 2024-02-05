package com.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Entity.Favoris;
import com.project.Entity.Jobs;
import com.project.Service.FavorisService;

@RestController
@RequestMapping("/api/v1/favoris")
public class FavorisController {
	@Autowired
    private FavorisService favorisService;

	
	@PostMapping
	public ResponseEntity<Favoris> addFavoris(@RequestBody Favoris favoris) {
		Favoris newfavoris = favorisService.addFavoris(favoris);
		return ResponseEntity.status(HttpStatus.CREATED).body(newfavoris);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Jobs>> getFavorisByCandidate(@PathVariable Long id){
		List<Jobs> jobs = favorisService.getFavoris(id);

        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(jobs);
        }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFavoris(@PathVariable Long id) {
		boolean deleted = favorisService.DeleteFavoris(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{candidateId}/{jobId}")
    public ResponseEntity<Void> deleteFavorisByCandidateAndJob(
            @PathVariable Long candidateId,
            @PathVariable Long jobId) {

        boolean deleted = favorisService.deleteFavorisByCandidateAndJob(candidateId, jobId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
