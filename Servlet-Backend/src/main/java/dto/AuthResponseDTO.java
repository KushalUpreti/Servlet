package dto;

public class AuthResponseDTO {
    private int id;
    private String email;
    private String jwtToken;

    public AuthResponseDTO(UserDTO user, String jwtToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.jwtToken = jwtToken;
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

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
