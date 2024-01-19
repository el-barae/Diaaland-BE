package com.project.Service;

import org.springframework.stereotype.Service;

import com.project.Entity.Favoris;
import com.project.Repository.FavorisRepository;

@Service
public class FavorisService {
	
	private final FavorisRepository favorisRepo;
	
	public FavorisService(FavorisRepository favorisRepo) {
		this.favorisRepo = favorisRepo;
	}
	
	public Favoris addFavoris(Favoris favoris) {
		return favorisRepo.save(favoris);
	}
	
	public boolean DeleteFavoris(Long id) {
		if(favorisRepo.existsById(id)) {
			favorisRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}
