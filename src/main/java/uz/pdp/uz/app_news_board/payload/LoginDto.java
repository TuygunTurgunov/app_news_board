package uz.pdp.uz.app_news_board.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {


        private String login;

        private String password;



}
