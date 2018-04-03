package vn.edu.topica.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
  private int code;

  private String message;
  public ApiResult(ErrorCode errorCode){
    this.code = errorCode.getCode();
    this.message = errorCode.name();
  }

}