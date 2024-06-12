from pydantic import BaseModel
from typing import Any, List, Dict, Optional

class CandidateDetails(BaseModel):
    candidateId: int
    skills: List[str]
    educations: List[Optional[str]]

class JobRequest(BaseModel):
    jobId: int
    jobDescription: str
    candidatesDetails: List[CandidateDetails]
    degrees: List[str]

class CandidateRequest(BaseModel):
    candidateId: int
    skills: List[str]
    educations: List[Optional[str]]
    jobsDetails: List[Dict[str, Any]]
