import requests

url = "http://localhost:8000/match_resumes/"

payload = {
    "jobId": 1,
    "jobDescription": "We are looking for a skilled React Developer to join our team. a developer master of javascript ,html and css.",
    "degrees": ["Bachelor", "Master"],
    "candidatesDetails": [
        {
            "candidateId": 2,
            "skills": ["Java", "C", "C++"],
            "educations": ["Bachelor", "Master", "PhD"]
        },
        {
            "candidateId": 4,
            "skills": [
                "Java",
                "C",
                "C++",
                "JavaScript",
                "Go",
                "HTML",
                "CSS",
                ".Net",
                "Linux",
                "Jquery",
                "Ajax",
                "Bootstrap",
                "Backend"
            ],
            "educations": [
                None,
                None,
                None,
                None,
                None,
                None,
                None
            ]
        }
    ]
}

headers = {
    "Content-Type": "application/json"
}

response = requests.post(url, json=payload, headers=headers)

print(f"Status Code: {response.status_code}")
print("Response JSON:")
print(response.json())


url = "http://localhost:8000/match_jobs/"

payload = {
    "candidateId": 2,
    "skills": ["Java", "C", "C++"],
    "educations": ["Bachelor", "Master", "PhD"],
    "jobsDetails": [
        {
            "jobId": 1,
            "description": "We are looking for a skilled React Developer to join our team. a developer master of javascript ,html and css.",
            "degrees": ["Bachelor", "Master"]
        },
        {
            "jobId": 2,
            "description": "Seeking a backend developer with experience in Java, C, and C++.",
            "degrees": ["Bachelor"]
        }
    ]
}

headers = {
    "Content-Type": "application/json"
}

response = requests.post(url, json=payload, headers=headers)

print(f"Status Code: {response.status_code}")
print("Response JSON:")
print(response.json())
