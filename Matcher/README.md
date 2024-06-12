# Matcher API

Matcher API is a FastAPI-based application for matching job candidates with job descriptions. It leverages semantic similarity to compare skills and education between candidates and job requirements.


## Installation

1. **Create and activate a virtual environment**:
    ```bash
    python -m venv venv

    # On Windows
    .\venv\Scripts\activate

    # On macOS/Linux
    source venv/bin/activate
    ```

2. **Install the dependencies**:
    ```bash
    pip install -r requirements.txt
    ```

## Usage

1. **Run the FastAPI application**:
    ```bash
    python app/main.py
    ```

2. **Access the API documentation**:
    Open your browser and navigate to `http://localhost:8000/docs` to see the interactive API documentation provided by Swagger UI.

## Endpoints

### Match Resumes

- **URL**: `/match_resumes/`
- **Method**: `POST`
- **Description**: Matches job candidates based on job description and degrees.
- **Request Body**:
    ```json
    {
        "jobId": 1,
        "jobDescription": "string",
        "candidatesDetails": [
            {
                "candidateId": 1,
                "skills": ["skill1", "skill2"],
                "educations": ["Bachelor"]
            }
        ],
        "degrees": ["Bachelor", "Master"]
    }
    ```
- **Response**:
    ```json
    [
        {
            "candidateId": 1,
            "skills": ["skill1", "skill2"],
            "educations": ["Bachelor"],
            "Degree job matching": 1,
            "Skills job matching": 0.75,
            "Matching score job": 0.8
        }
    ]
    ```

### Match Jobs

- **URL**: `/match_jobs/`
- **Method**: `POST`
- **Description**: Matches jobs to a candidate's skills and education.
- **Request Body**:
    ```json
    {
        "candidateId": 1,
        "skills": ["skill1", "skill2"],
        "educations": ["Bachelor"],
        "jobsDetails": [
            {
                "jobId": 1,
                "description": "string",
                "degrees": ["Bachelor", "Master"]
            }
        ]
    }
    ```
- **Response**:
    ```json
    [
        {
            "jobId": 1,
            "description": "string",
            "degrees": ["Bachelor", "Master"],
            "Degree job matching": 1,
            "Skills job matching": 0.75,
            "Matching score job": 0.8
        }
    ]
    ```


## Dependencies

- `fastapi`
- `uvicorn`
- `pydantic`
- `sentence-transformers`
- `numpy`

Install all dependencies via `pip` using the `requirements.txt` file provided.


