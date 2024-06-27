import logging
from fastapi import FastAPI
from app.api.endpoints import router as api_router
from app.api.pdf_extractor import router as pdf_extractor_router


logging.basicConfig(level=logging.INFO)

app = FastAPI()

# Include the API router
app.include_router(api_router)
#app.include_router(pdf_extractor_router, prefix="/pdf", tags=["PDF Extraction"])

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
