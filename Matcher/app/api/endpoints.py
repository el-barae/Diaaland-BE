from fastapi import APIRouter, HTTPException, Depends
from app.schemas import JobRequest, CandidateRequest
from app.matcher import matcher
import logging

router = APIRouter()

@router.post("/match_resumes/")
async def match_resumes(job_request: JobRequest):
    try:
        candidates_data = [candidate.dict() for candidate in job_request.candidatesDetails]
        job_description = job_request.jobDescription
        job_degrees = job_request.degrees
        result = matcher.matching_score_by_job(candidates_data, job_description, job_degrees)
        return result
    except Exception as e:
        logging.error(f"Error matching resumes: {e}")
        raise HTTPException(status_code=500, detail="Internal Server Error")

@router.post("/match_jobs/")
async def match_jobs(candidate_request: CandidateRequest):
    try:
        candidate_skills = candidate_request.skills
        candidate_degrees = candidate_request.educations
        jobs_data = candidate_request.jobsDetails
        result = matcher.matching_score_by_candidates(jobs_data, candidate_skills, candidate_degrees)
        return result
    except Exception as e:
        logging.error(f"Error matching jobs: {e}")
        raise HTTPException(status_code=500, detail="Internal Server Error")
