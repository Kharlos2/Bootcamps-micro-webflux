package co.com.pragma.model.bootcamp.exceptions;

public class HttpException extends Exception {
  private final int status;

  public HttpException(int httpStatus, String message) {
    super(message);
    this.status = httpStatus;
  }

  public int getStatus() {
    return status;
  }
}
