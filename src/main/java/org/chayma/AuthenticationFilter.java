package org.chayma;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // 1. Jib l-Header li fih "Authorization"
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

        // 2. Ila ma-kanch l-Header, bloccih (401 Unauthorized)
        if (authHeader == null || authHeader.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Access denied! Please provide credentials.").build());
            return;
        }

        // 3. Jib l-Username w Password (kay-kounou Crypté b Base64)
        String encodedUserPassword = authHeader.get(0).replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword));

        // 4. Ferrqhom (Username : Password)
        StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();

        // 5. Vérifier wach s7a7 (Hna darna "admin" w "admin123")
        if ("admin".equals(username) && "admin123".equals(password)) {
            return; // Dourouz! (Access granted)
        }

        // 6. Ila ghaltin, bloccih
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Access denied! Wrong username or password.").build());
    }
}
