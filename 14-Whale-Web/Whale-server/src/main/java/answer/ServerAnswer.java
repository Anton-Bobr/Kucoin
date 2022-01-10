package answer;

import java.sql.SQLException;

public abstract class ServerAnswer {
    public abstract String buildAnswer (String clientRequest) throws Exception;
}
