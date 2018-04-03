package vn.edu.topica.constant;

public enum ErrorCode {
  SUCCESS(200),
  NOT_FOUND(404),

  SYSTEM_ERROR(500);

  int code;

  ErrorCode(int code){
    this.code = code;
  }

  public int getCode(){
    return this.code;
  }

}
