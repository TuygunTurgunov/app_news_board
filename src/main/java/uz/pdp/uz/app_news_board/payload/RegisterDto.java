package uz.pdp.uz.app_news_board.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {


    @NotNull(message = "login bo'sh bo'lmasin")
    @Size(min = 3,max = 50)
    private String login;

    @NotNull(message = "password bo'sh bo'lmasin")
    private String password;

    @NotNull(message = "email bo'sh bo'lmasin")
    @Email
    private String email;

    @NotNull(message = "yosh bo'sh bo'lmasin")
    private Integer age;





}
