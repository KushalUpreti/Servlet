package dto;

import java.util.List;

public class AuthResponseDTO {
    private int id;
    private String email;
    private String token;
    private List<String> role;

    public AuthResponseDTO(UserDTO user, String token, List<String> role) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.token = token;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
