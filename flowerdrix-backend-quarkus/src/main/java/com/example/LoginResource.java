package com.example;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("/api")
public class LoginResource {
    @Inject
    DataSource dataSource;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserCredentials credentials) {
        boolean isValidUser = validateUser(credentials.getUser(), credentials.getPass());

        if (isValidUser) {
            return Response.ok().entity("Login successful").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }

    private boolean validateUser(String username, String password) {
        // Consulta SQL para validar las credenciales
        String query = "SELECT COUNT(*) FROM login WHERE user = ? AND pass = ?"; // Asegúrate de que el nombre de la tabla sea correcto
        try (Connection conn = dataSource.getConnection(); // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(query)) { // Preparar la consulta
            stmt.setString(1, username); // Establecer el nombre de usuario
            stmt.setString(2, password); // Establecer la contraseña
            ResultSet rs = stmt.executeQuery(); // Ejecutar la consulta
            if (rs.next()) {
                return rs.getInt(1) > 0; // Devolver verdadero si hay al menos un registro que coincida
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir cualquier error en la consola
        }
        return false; // Si no se encontró al usuario, devolver falso
    }
}


