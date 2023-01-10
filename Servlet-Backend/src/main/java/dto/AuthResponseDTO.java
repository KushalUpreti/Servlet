package dto;

public class AuthResponseDTO {
    private int id;
    private String email;
    private String token;
    private String role = "ADMIN";

    public AuthResponseDTO(UserDTO user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.token = token;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
