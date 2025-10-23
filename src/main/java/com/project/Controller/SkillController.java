package com.project.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.project.Entity.Skills;
import com.project.Service.SkillService;


@RestController
@RequestMapping("/api/v1/profiles/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skills> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skills> getSkillById(@PathVariable Long id) {
        Skills skill = skillService.getSkillById(id);
        if (skill != null) {
            return ResponseEntity.ok(skill);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/id/{name}")
    public Long getIdByName(@PathVariable String name) {
    	return skillService.getIdByName(name);
    }

    @PostMapping
    public ResponseEntity<Skills> createSkill(@RequestBody Skills skill) {
        Skills newSkill = skillService.createSkill(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skills> updateSkill(@PathVariable Long id, @RequestBody Skills skill) {
        Skills updatedSkill = skillService.updateSkill(id, skill);
        if (updatedSkill != null) {
            return ResponseEntity.ok(updatedSkill);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        boolean deleted = skillService.deleteSkill(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /*@PostMapping("/fakeskill")
>>>>>>> 69b6baa (to intellij idea)
    public Skills createFakeSkill() {
        Faker faker = new Faker();
        Skills skill = new Skills();
        
        skill.setName(faker.job().position());
        skill.setType(faker.options().option("Technical", "Soft"));
        
        skillService.createSkill(skill);
        
        return skill;
<<<<<<< HEAD
    }
=======
    }*/

}
