package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Favoris;
import com.project.Entity.Jobs;
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
	
	public List<Jobs> getFavoris(Long id) {
		return favorisRepo.findJobsByCandidateId(id);
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
