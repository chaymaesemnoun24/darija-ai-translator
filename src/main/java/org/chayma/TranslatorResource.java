package org.chayma;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Path("/translator")
public class TranslatorResource {

    // 🔴 L-Key
    private static final String API_KEY = "AIzaSyCn8qCMs7c8o5nGpMDvLJXl3Hdhr51zMf4";

    @GET
    @Produces("text/plain; charset=UTF-8")
    public String translate(@QueryParam("text") String text) {

        if (text == null || text.isEmpty()) {
            return "Sifet liya chi haja n-terjemha!";
        }

        try {

            String prompt = "Translate this text to Moroccan Darija. Give ONLY the translation, no extra notes: " + text;

            String jsonBody = "{"
                    + "\"contents\": [{"
                    + "\"parts\":[{\"text\": \"" + prompt + "\"}]"
                    + "}]"
                    + "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            return extractTranslation(response.body());

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 👇 Method l-cleaner 👇
    private String extractTranslation(String jsonResponse) {
        try {
            String marker = "\"text\": \"";
            int startIndex = jsonResponse.indexOf(marker);

            if (startIndex != -1) {
                startIndex += marker.length();
                int endIndex = startIndex;

                // Boucle bach n-lqaw fin kat-sali l-kettba
                while (endIndex < jsonResponse.length()) {
                    endIndex = jsonResponse.indexOf("\"", endIndex);
                    if (endIndex == -1) break;
                    if (jsonResponse.charAt(endIndex - 1) != '\\') {
                        break;
                    }
                    endIndex++;
                }

                if (endIndex != -1) {
                    String translation = jsonResponse.substring(startIndex, endIndex);
                    return translation.replace("\\n", "\n").replace("\\\"", "\"");
                }
            }
            
            return jsonResponse;
        } catch (Exception e) {
            return jsonResponse;
        }
    }
}