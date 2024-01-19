package com.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.Entity.Favoris;
import com.project.Service.FavorisService;

@Controller
public class FavorisController {
	FavorisService favorisService;
	
	@PostMapping
	public Favoris addFavoris(@RequestBody Favoris favoris) {
		return favorisService.addFavoris(favoris);
	}
	
	@PostMapping
	public 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFavoris(@PathVariable Long id) {
		boolean deleted = favorisService.DeleteFavoris(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
	}
}
