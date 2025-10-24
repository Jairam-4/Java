from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import datetime
import wikipedia

# Text-to-speech disabled on server; frontend will speak.
# We keep responses as text and structured actions.

app = FastAPI()

# Allow frontend to call this API from any origin during development.
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class IntentRequest(BaseModel):
    transcript: str

class IntentResponse(BaseModel):
    response: str
    actions: list[str] = []  # e.g., ["open:https://..."]


def build_open_action(url: str) -> str:
    return f"open:{url}"


def process_command_server(cmd: str) -> IntentResponse:
    cmd = (cmd or "").lower().strip()
    if not cmd:
        return IntentResponse(response="I didn't catch that. Could you repeat?", actions=[])

    # Simple intents mapped to URLs
    url_map = {
        "instagram": "https://www.instagram.com",
        "whatsapp": "https://web.whatsapp.com",
        "youtube": "https://www.youtube.com",
        "facebook": "https://www.facebook.com",
        "gmail": "https://mail.google.com",
        "mail": "https://mail.google.com",
        "github": "https://www.github.com",
        "google": "https://www.google.com",
        "spotify": "https://www.spotify.com",
        "chess": "https://www.chess.com",
        "snapchat": "https://www.snapchat.com",
    }

    for key, url in url_map.items():
        if key in cmd or f"open {key}" in cmd:
            return IntentResponse(response=f"Opening {key}.", actions=[build_open_action(url)])

    # Search intent
    if cmd.startswith("search "):
        query = cmd.replace("search", "", 1).strip()
        if not query:
            return IntentResponse(response="What should I search for?", actions=[])
        q = query.replace(" ", "+")
        return IntentResponse(response=f"Searching Google for {query}", actions=[build_open_action(f"https://www.google.com/search?q={q}")])

    # Wikipedia intent
    if cmd.startswith("who is ") or cmd.startswith("what is "):
        query = cmd.replace("who is", "").replace("what is", "").strip()
        if query:
            try:
                summary = wikipedia.summary(query, sentences=2)
                return IntentResponse(response=summary, actions=[])
            except Exception:
                return IntentResponse(response="I couldn't find that on Wikipedia.", actions=[])
        else:
            return IntentResponse(response="What should I search on Wikipedia?", actions=[])

    # Time and date intents
    if "time" in cmd:
        now = datetime.datetime.now().strftime("%I:%M %p")
        return IntentResponse(response=f"The time is {now}", actions=[])

    if "date" in cmd:
        today = datetime.date.today().strftime("%B %d, %Y")
        return IntentResponse(response=f"Today is {today}", actions=[])

    if any(x in cmd for x in ("exit", "quit", "stop", "goodbye")):
        return IntentResponse(response="Goodbye.", actions=[])

    # Fallback web search
    q = cmd.replace(" ", "+")
    return IntentResponse(response=f"I will search the web for {cmd}", actions=[build_open_action(f"https://www.google.com/search?q={q}")])


@app.post("/intent", response_model=IntentResponse)
async def intent_endpoint(req: IntentRequest):
    return process_command_server(req.transcript)
