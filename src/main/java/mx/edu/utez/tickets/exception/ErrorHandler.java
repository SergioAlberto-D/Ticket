package mx.edu.utez.tickets.exception;

public class ErrorHandler extends RuntimeException {
  public ErrorHandler(String message) {
    super(message);
  }
}
