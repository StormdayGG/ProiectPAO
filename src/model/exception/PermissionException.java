package model.exception;

public class PermissionException extends Exception{
    public PermissionException(String errorMessage)
    {
        super(errorMessage);
    }
}
