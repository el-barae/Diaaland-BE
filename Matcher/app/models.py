
from sentence_transformers import SentenceTransformer, util
import numpy as np
import logging
from typing import List, Dict, Any

class Matcher:
    def __init__(self, model: SentenceTransformer, degree_importance: Dict[str, int]):
        self.model = model
        self.degree_importance = degree_importance

    def semantic_similarity(self, job_feature: str, resume_feature: str) -> float:
        try:
            job_embeddings = self.model.encode(job_feature, convert_to_tensor=True)
            resume_embeddings = self.model.encode(resume_feature, convert_to_tensor=True)
            cosine_scores = util.pytorch_cos_sim(job_embeddings, resume_embeddings)
            similarity_score = np.mean(cosine_scores.cpu().numpy())
            return similarity_score
        except Exception as e:
            logging.error(f"Error calculating semantic similarity: {e}")
            return 0.0

    def degree_matching(self, candidates: List[Dict[str, Any]], job_degrees: List[str]) -> List[Dict[str, Any]]:
        min_required_degree = min(job_degrees, key=lambda degree: self.degree_importance.get(degree, 0))
        degree_measure = 'Degree job matching'

        for i, candidate in enumerate(candidates):
            candidate_degrees = candidate.get('educations', [])
            if not candidate_degrees:
                candidates[i][degree_measure] = 0
                continue

            candidate_degrees = [degree for degree in candidate_degrees if degree is not None]
            if not candidate_degrees:
                candidates[i][degree_measure] = 0
                continue

            max_candidate_degree = max(candidate_degrees, key=lambda degree: self.degree_importance.get(degree, 0))
            candidates[i][degree_measure] = int(self.degree_importance.get(max_candidate_degree, 0) >= self.degree_importance.get(min_required_degree, 0))
        return candidates

    def skills_matching(self, candidates: List[Dict[str, Any]], job_skills: List[str]) -> List[Dict[str, Any]]:
        job_skills_measure = 'Skills job matching'

        for i, candidate in enumerate(candidates):
            candidate_skills = candidate.get('skills', [])
            if not candidate_skills:
                candidates[i][job_skills_measure] = 0.0
                continue

            score = 0
            for job_skill in job_skills:
                if job_skill in candidate_skills:
                    score += 1
                else:
                    similarity_scores = [self.semantic_similarity(job_skill, candidate_skill) for candidate_skill in candidate_skills]
                    max_similarity = max(similarity_scores, default=0.0)
                    score += max_similarity

            avg_score = score / len(job_skills) if job_skills else 0
            candidates[i][job_skills_measure] = avg_score
        return candidates

    def matching_score_by_job(self, candidates: List[Dict[str, Any]], job_description: str, job_degrees: List[str]) -> List[Dict[str, Any]]:
        candidates = self.degree_matching(candidates, job_degrees)
        job_skills = job_description.split()  # Extract skills from job description for simplicity
        candidates = self.skills_matching(candidates, job_skills)
        matching_score_key = "Matching score job"

        for i, candidate in enumerate(candidates):
            degree_score = candidate.get('Degree job matching', 0)
            skills_score = candidate.get('Skills job matching', 0)
            candidates[i][matching_score_key] = round(0.2 * degree_score + 0.8 * skills_score, 2)

        return candidates

    def matching_score_by_candidates(self, jobs: List[Dict[str, Any]], candidate_skills: List[str], candidate_degrees: List[str]) -> List[Dict[str, Any]]:
        candidate_data = [{'skills': candidate_skills, 'educations': candidate_degrees}]
        matching_jobs = []

        for job in jobs:
            job_description = job['description']
            job_degrees = job['degrees']
            matched_candidate = self.matching_score_by_job(candidate_data, job_description, job_degrees)[0]
            matched_job = job.copy()
            matched_job.update(matched_candidate)
            matching_jobs.append(matched_job)

        return matching_jobs
