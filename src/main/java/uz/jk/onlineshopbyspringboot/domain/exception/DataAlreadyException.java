package uz.jk.onlineshopbyspringboot.domain.exception;

public class DataAlreadyException extends RuntimeException{
    public DataAlreadyException(String message) {
        super(message);
    }
}
