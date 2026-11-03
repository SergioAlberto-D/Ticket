package mx.edu.utez.tickets.exception.custom;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String mensaje){
        super(mensaje);
    }
}
