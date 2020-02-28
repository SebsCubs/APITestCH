package helpers.entities;


public class Authorization {
    private String request_token;
    private String session_id;
    private String username;
    private String password;
    private String success;
    private String expires_at;


    public Authorization(){}

    public Authorization(String request_token){
        this.request_token = request_token;
    }

    public String getRequest_token() {
        return request_token;
    }
    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getSession_id() { return session_id; }
    public void setSession_id(String session_id) { this.session_id = session_id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSuccess(){return success;}
    public void setSuccess(String success) { this.success = success; }

    public String getExpires_at() { return expires_at; }
    public void setExpires_at(String expires_at) { this.expires_at = expires_at; }
}
