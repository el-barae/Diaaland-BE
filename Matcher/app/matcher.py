from sentence_transformers import SentenceTransformer
from app.models import Matcher

degree_importance = {'Bachelor': 1, 'Master': 2, 'PhD': 3}  # This can be extended as needed

# Load the model globally
model = SentenceTransformer("distilbert-base-nli-stsb-mean-tokens")
#model = SentenceTransformer("distilbert-base-nli-stsb-mean-tokens", cache_folder=None, force_download=True)
matcher = Matcher(model, degree_importance)
