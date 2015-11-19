package limmen.hw2.client.util;

/* RejectedException is thrown when a transaction could'nt be done */
final public class RejectedException extends Exception {
    private static final long serialVersionUID = -314439670131687936L;

    public RejectedException(String reason) {
        super(reason);
    }
}
