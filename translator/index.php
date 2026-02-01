<?php
// --- CONFIGURATION ---
$apiUrl = "http://localhost:8080/api/translator";
// Password: admin / admin123 (Base64 Encoded)
$authHeader = "Authorization: Basic YWRtaW46YWRtaW4xMjM="; 

// --- LOGIC: HIKMA OF THE DAY ---
$hikmas = [
    "\"Lii ma 3ndu lsan, ma 3ndu hbib.\" (Language is key to friendship)",
    
];
$randomHikma = $hikmas[array_rand($hikmas)];

// --- LOGIC: HANDLE TRANSLATION (METHODE SAHLA: FILE_GET_CONTENTS) ---
$translation = "";
$error = "";
$inputText = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $inputText = trim($_POST['text']);
    
    if (!empty($inputText)) {
        // 1. Prepare URL
        $urlWithParams = $apiUrl . "?text=" . urlencode($inputText);

        // 2. Prepare Options (Bhal l-code l-qdim)
        $options = [
            "http" => [
                "method" => "GET",
                "header" => $authHeader
            ]
        ];

        // 3. Create Context
        $context = stream_context_create($options);

        // 4. Call Java API (Sans cURL)
        // Darna '@' bach n-khbbiw warning ila l-server tafi
        $response = @file_get_contents($urlWithParams, false, $context);

        if ($response === FALSE) {
            $error = "Error: Java Server is offline or Password incorrect!";
        } else {
            $translation = $response;
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Darija AI Client</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&family=Reem+Kufi:wght@500&display=swap" rel="stylesheet">
    
    <style>
        /* --- DESIGN DU THEME "MERCI" (Royal Bordeau & Gold) --- */
        body {
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #2b0a16 0%, #4a1225 100%);
            color: white;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            border: 1px solid rgba(255,255,255,0.1);
            width: 100%;
            max-width: 500px;
            text-align: center;
        }

        .logo {
            font-size: 50px;
            margin-bottom: 10px;
            text-shadow: 0 0 20px rgba(212, 175, 55, 0.5);
        }
        
        h1 {
            color: #d4af37; /* Gold */
            margin: 0;
            font-weight: 600;
            letter-spacing: 1px;
        }

        p.subtitle {
            color: rgba(255,255,255,0.7);
            font-size: 0.9rem;
            margin-top: 5px;
        }

        .hikma-box {
            margin: 20px 0;
            padding: 15px;
            background: rgba(0, 0, 0, 0.2);
            border-left: 4px solid #d4af37;
            border-radius: 4px;
            text-align: left;
        }

        .hikma-text {
            font-family: 'Reem Kufi', sans-serif;
            color: #f0e68c;
            font-style: italic;
            font-size: 0.95rem;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        textarea {
            width: 100%;
            height: 100px;
            padding: 15px;
            border-radius: 12px;
            border: none;
            background: rgba(255, 255, 255, 0.9);
            color: #333;
            font-family: inherit;
            resize: none;
            font-size: 16px;
            box-sizing: border-box;
        }

        textarea:focus {
            outline: 3px solid rgba(212, 175, 55, 0.5);
        }

        button {
            padding: 15px;
            border: none;
            border-radius: 30px;
            background: linear-gradient(to right, #d4af37, #c5a028);
            color: #2b0a16;
            font-weight: bold;
            font-size: 16px;
            cursor: pointer;
            transition: 0.3s;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        button:hover {
            transform: scale(1.02);
            box-shadow: 0 5px 20px rgba(212, 175, 55, 0.4);
        }

        .result-box {
            margin-top: 25px;
            padding: 20px;
            background: rgba(255, 255, 255, 0.15);
            border-radius: 12px;
            text-align: left;
            animation: fadeIn 0.5s ease-in;
        }

        .label {
            font-size: 12px;
            text-transform: uppercase;
            color: #d4af37;
            letter-spacing: 1px;
            margin-bottom: 5px;
            display: block;
        }

        .translation-text {
            font-size: 1.2rem;
            font-weight: 500;
        }

        .error {
            color: #ffcccb;
            background: rgba(255, 0, 0, 0.2);
            padding: 10px;
            border-radius: 8px;
            margin-top: 20px;
        }

        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
    </style>
</head>
<body>

    <div class="container">
        <div class="logo"><img src="robot-zellij.png" alt="Zellige Robot Logo" width="70" height="70" style="border-radius: 50%;"></div>
        <h1>Darija Translator</h1>
        <p class="subtitle">Powered by Java REST API & Gemini AI</p>

        <div class="hikma-box">
            <span class="hikma-text"><?php echo $randomHikma; ?></span>
        </div>

        <form method="POST" action="">
            <textarea name="text" placeholder="Type English text here..." required><?php echo htmlspecialchars($inputText); ?></textarea>
            <button type="submit">✨ Translate Now</button>
        </form>

        <?php if (!empty($translation)): ?>
            <div class="result-box">
                <span class="label">Translation (Darija):</span>
                <div class="translation-text">
                    <?php echo htmlspecialchars($translation); ?>
                </div>
            </div>
        <?php endif; ?>

        <?php if (!empty($error)): ?>
            <div class="error">
                ⚠️ <?php echo $error; ?>
            </div>
        <?php endif; ?>

    </div>

</body>
</html>