package demo.security.dto;

import java.util.List;

public class UserView {
    private String username;
    private List<String> authorities;
    private boolean accountNonLocked;

    public UserView(String username, List<String> authorities, boolean accountNonLocked) {
        this.username = username;
        this.authorities = authorities;
        this.accountNonLocked = accountNonLocked;
    }

    public String getUsername() { return username; }
    public List<String> getAuthorities() { return authorities; }
    public boolean isAccountNonLocked() { return accountNonLocked; }

    public void setUsername(String username) { this.username = username; }
    public void setAuthorities(List<String> authorities) { this.authorities = authorities; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
}
