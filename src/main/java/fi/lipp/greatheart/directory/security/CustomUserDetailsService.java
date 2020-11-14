package fi.lipp.greatheart.directory.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class CustomUserDetailsService {

    private String SECURITY_PATH = "http://localhost:8080/validate_token";

    public Optional<CustomUserDetails> getUserFromToken(String token) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SECURITY_PATH))
                    .POST(HttpRequest.BodyPublishers.ofString(token))
                    .header("Authorization","Bearer " + token)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(response.body(), User.class);
                return Optional.of(new CustomUserDetails(user));
            }
        } catch (Exception e) { }
        return Optional.empty();
    }
}
