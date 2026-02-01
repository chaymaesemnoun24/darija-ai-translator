package org.chayma; // Ta'kdi mn smiyet l-package dyalk

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching // HADI HIYA S-SER: Kat-khlli l-Filter y-khdem hwa l-lewwl
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    /**
     * Hada kay-traite l-Request melli yallah dakhla
     * Ila kan Chrome kay-sowl "OPTIONS", kan-jawbouh dghya b "OK"
     */
    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            request.abortWith(Response.ok().build());
        }
    }

    /**
     * Hada kay-zid l-Headers f l-Response bach Chrome y-fr7
     */
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {

        // 1. N-choufu mnin jay l-request (Extension ID)
        String origin = request.getHeaderString("Origin");
        if (origin == null) {
            origin = "*"; // Ila kan Postman aw browser 3adi
        }

        // 2. N-rejou l-Origin howa nits (hit Credentials=true ma-kay-bghich *)
        response.getHeaders().add("Access-Control-Allow-Origin", origin);

        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");

        response.getHeaders().add("Access-Control-Allow-Credentials", "true");

        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}