package uz.jk.onlineshopbyspringboot.domain.exception;

public class DataNotMatchedException extends RuntimeException{
    public DataNotMatchedException(String message) {
        super(message);
    }
}
