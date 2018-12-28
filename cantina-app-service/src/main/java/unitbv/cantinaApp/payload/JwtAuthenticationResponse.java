package unitbv.cantinaApp.payload;

import java.util.Collection;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Collection<?> roles;
    private String email;
    private Integer tokenExpiration;

    public JwtAuthenticationResponse(String accessToken, Collection<?> roles, String email, Integer tokenExpiration) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.email = email;
        this.tokenExpiration = tokenExpiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	public Collection<?> getRoles() {
		return roles;
	}

	public void setRoles(Collection<?> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(Integer tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
    
}
