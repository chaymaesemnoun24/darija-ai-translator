// 1. Array of Moroccan Wisdom (Hikma)
const hikmas = [
    "\"Li ma 3ndu lsan, ma 3ndu hbib.\" (Language is key to friendship)"
];

// Display Random Hikma on Load
document.addEventListener('DOMContentLoaded', () => {
    const randomHikma = hikmas[Math.floor(Math.random() * hikmas.length)];
    document.getElementById('hikmaText').innerText = randomHikma;
});

// 2. Translation Logic
document.getElementById('translateBtn').addEventListener('click', async () => {
    const text = document.getElementById('inputText').value;
    const resultDiv = document.getElementById('result');
    const speakBtn = document.getElementById('speakBtn');
    const btnText = document.getElementById('translateBtn');

    // Reset UI
    resultDiv.style.display = 'none';
    speakBtn.style.display = 'none';

    if (!text) {
        resultDiv.style.display = 'block';
        resultDiv.innerText = "⚠️ Please write something first!";
        resultDiv.style.color = "#ffcccb"; // Light red
        return;
    }

    // Show Loading Animation on Button
    btnText.innerHTML = '<div class="loader"></div> Translating...';

    try {
        const response = await fetch(`http://localhost:8080/api/translator?text=${encodeURIComponent(text)}`, {
            method: 'GET',
            headers: {
                'Authorization': 'Basic YWRtaW46YWRtaW4xMjM=', 
                'Content-Type': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`Server Error: ${response.status}`);
        }
        
        const translation = await response.text();
        
        // Show Result
        resultDiv.style.display = 'block';
        resultDiv.style.color = "#fff";
        resultDiv.innerText = translation;
        
        // Show Speak Button
        speakBtn.style.display = 'flex';
        
        // Reset Button Text
        btnText.innerHTML = '✨ Translate to Darija';

        // Voice Feature
        speakBtn.onclick = () => {
            let speech = new SpeechSynthesisUtterance(translation);
            speech.lang = "ar"; 
            speech.rate = 0.9;
            window.speechSynthesis.speak(speech);
        };
        
    } catch (error) {
        resultDiv.style.display = 'block';
        resultDiv.innerText = "Error: Java Server is offline!";
        resultDiv.style.color = "#ffcccb";
        btnText.innerHTML = '✨ Translate to Darija';
        console.error(error);
    }
});