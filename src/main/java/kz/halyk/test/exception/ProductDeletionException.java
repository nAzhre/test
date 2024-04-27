package kz.halyk.test.exception;

public class ProductDeletionException extends RuntimeException {

    public ProductDeletionException(String message) {
        super(message);
    }

    public ProductDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
