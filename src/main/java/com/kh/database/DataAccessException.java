package com.kh.database;

public class DataAccessException extends RuntimeException {

  private static final long SERIAL_VERSION_UID = 1L;

  public DataAccessException() {
    super();
  }

  public DataAccessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTree) {
    super(message, cause, enableSuppression, writableStackTree);
  }

  public DataAccessException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataAccessException(String message) {
    super(message);
  }

  public DataAccessException(Throwable cause) {
    super(cause);
  }

}
