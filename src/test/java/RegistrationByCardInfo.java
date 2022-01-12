public class RegistrationByCardInfo {
    private final String login;
    private final String password;
    private final String status;

    public RegistrationByCardInfo(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}