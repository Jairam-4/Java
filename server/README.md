Run the API locally:

```bash
python3 -m pip install --user -r server/requirements.txt
python3 -m uvicorn server.main:app --reload --host 0.0.0.0 --port 8000
```

Endpoint:
- POST /intent { transcript: string } -> { response: string, actions: string[] }

Actions format:
- "open:https://..." means the client should open this URL.
