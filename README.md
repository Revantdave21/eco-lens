EcoLens 🌱
EcoLens Campus AI is an Android sustainability companion designed to help campus communities make more environmentally responsible choices. It combines an AI-assisted waste audit experience with campus sustainability insights, estimated environmental impact, and gamified eco-points.
This repository contains an Android application built with Kotlin and Jetpack Compose, plus an optional FastAPI vision-auditing service.

✨ Features
- AI-assisted waste scanning — analyze an item or campus waste image and display a suggested category and disposal route.
- Environmental impact estimates — present estimated carbon footprint, water footprint, and decomposition time for scanned items.
- Actionable sustainability tips — get practical suggestions for reducing waste.
- Eco-points and scan history — award points for completed audits and keep a history of results in the app's UI state.
- Campus sustainability dashboard — review campus metrics, weekly reports, and mitigation/simulation controls.
- Demo and fallback support — the Python service includes sample results to keep demonstrations usable when Gemini is not configured or a request fails.
Environmental impact values are estimates, not laboratory measurements. Follow local campus and municipal waste-disposal guidance when in doubt.
🧰 Tech stack
Android app
- Kotlin
- Jetpack Compose and Material 3
- Android Gradle Plugin / Gradle Kotlin DSL
- AndroidX ViewModel and Coroutines
- Retrofit / OkHttp and Moshi libraries
- Firebase-related libraries are included in the Gradle configuration
Optional AI backend
- Python 3
- FastAPI and Uvicorn
- Google Generative AI SDK (Gemini)
- Pillow for image decoding
- Pydantic for response validation
📁 Project structure
.
├── app/                         # Android application
│   └── src/main/java/com/example/
│       ├── data/                # Models and repository logic
│       ├── ui/                  # Compose UI, screens, components, theme
│       └── ...
├── ecolens_service.py           # Optional FastAPI image-auditing API
├── requirements.txt             # Python backend dependencies
├── .env.example                 # Example Gemini environment configuration
├── build.gradle.kts             # Root Gradle configuration
└── settings.gradle.kts
🚀 Getting started
Requirements
- Android Studio with a compatible Android SDK
- JDK 11 or a JDK supported by the Android Gradle Plugin in your installed Android Studio version
- Python 3.10+ recommended for the optional backend
- A Gemini API key if you want to use Gemini-backed analysis in the Python service
1. Clone the repository
git clone https://github.com/<your-username>/EcoLens.git
cd EcoLens
Replace the repository URL with your GitHub repository URL.
2. Run the Android app
1. Open the project root in Android Studio.
2. Allow Gradle to sync and install any required Android SDK components.
3. Select an emulator or connected Android device.
4. Run the app configuration.
The project is configured with a minimum Android SDK level of 24. Some integrations may require additional configuration depending on your environment. Do not commit API keys, signing credentials, or other secrets.
3. Start the optional Python backend
From the project root, create and activate a virtual environment, then install the dependencies:
Windows (PowerShell)
py -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
macOS / Linux
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
Set your Gemini API key as an environment variable before starting the service.
Windows (PowerShell)
$env:GEMINI_API_KEY="your_gemini_api_key"
python ecolens_service.py
macOS / Linux
export GEMINI_API_KEY="your_gemini_api_key"
python ecolens_service.py
The API listens on http://localhost:8000 by default. Open http://localhost:8000/docs to explore the interactive FastAPI documentation.
Security: Keep your real key out of source control. .env.example is only a template; do not replace it with a file containing your real credentials. For deployment, configure secrets through your hosting provider's secret/environment-variable settings.

🔌 Backend API
Method	Endpoint	Purpose
GET	/	Service information and status
GET	/api/health	Health check and Gemini configuration status
POST	/api/scan	Analyze an image sent as multipart form data (file) or form field (image_base64)
POST	/api/scan/json	Analyze an image supplied as JSON with an image_base64 field


Example request
curl -X POST "http://localhost:8000/api/scan" \
  -F "file=@sample-waste.jpg"
A successful scan returns structured JSON containing the detected item, category, recommended disposal bin and campus location, estimated environmental impact, actionable advice, and eco-points.
For an Android emulator, localhost refers to the emulator itself. If the Android app needs to call a backend running on your development computer, use the emulator's host-access address (commonly 10.0.2.2:8000) and make sure the app's configured API base URL points to it. A physical device generally needs the computer's reachable LAN IP address, with both devices on the same network and firewall rules allowing the connection. The backend URL may need to be configured in the app before using the service from a device.
🔐 Configuration
- GEMINI_API_KEY — optional for the Python backend's live Gemini analysis. If it is missing, the backend logs a warning and returns sample fallback results.
- Android backend URL — configure the API base URL for your own local or deployed backend if you intend to connect the app to this service.
- Firebase / Google services — if your chosen build or deployment uses Firebase features, add the required project configuration privately. Do not commit service-account keys or signing files.
🧪 Tests
The Android project includes unit and instrumented test source sets. Run the available unit tests from the project root with:
./gradlew test
On Windows, use:
.\gradlew.bat test
Instrumented tests require a configured emulator or Android device.
🖼️ Screenshots
Add screenshots or a short demo GIF here when available. For example:
![EcoLens dashboard](docs/screenshots/dashboard.png)
![EcoLens scan results](docs/screenshots/scan-results.png)
🤝 Contributing
Contributions and suggestions are welcome. A useful contribution workflow is:
1. Fork the repository.
2. Create a feature branch.
3. Make and test your changes.
4. Open a pull request describing the change.
Please avoid committing API keys, local .env files, signing keys, or other sensitive configuration.
📄 License
No license file was present in the supplied project archive. Add a LICENSE file before describing this project as open source, and update this section to match the license you choose.
Built to make campus sustainability easier to understand, measure, and act on. 🌍
