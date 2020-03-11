package entities;

public class AuthorizationBuilder {
    private Authorization authorization;

    public AuthorizationBuilder() {
        authorization = new Authorization();
    }

    public AuthorizationBuilder(Authorization authorization) {
        this.authorization = authorization;
    }

    public AuthorizationBuilder withRequestToken(String requestToken) {
        authorization.setRequest_token(requestToken);
        return this;
    }

    public AuthorizationBuilder withSession_id(String session_id) {
        authorization.setSession_id(session_id);
        return this;
    }

    public AuthorizationBuilder withUsername(String username) {
        authorization.setUsername(username);
        return this;
    }

    public AuthorizationBuilder withPassword(String password) {
        authorization.setPassword(password);
        return this;
    }

    public AuthorizationBuilder withSuccess(String success) {
        authorization.setSuccess(success);
        return this;
    }

    public AuthorizationBuilder withExpires_at(String expires_at) {
        authorization.setExpires_at(expires_at);
        return this;
    }

    public Authorization build() {
        return this.authorization;
    }
}