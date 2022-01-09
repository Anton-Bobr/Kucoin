package answer;

public class WhaleTransactionServerAnswer extends ServerAnswer{
    @Override
    public String buildAnswer(String clientRequest) {

        return "WhaleTransaction  " + clientRequest;
    }
}
