package uz.pdp.uz.app_news_board.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsAddDto {


    @NotNull(message = "Header of news not be empty")
    private String newsHeader;

    @NotNull(message = "Text of news not be empty")
    @Size(max = 500,message = "Max size of message is 500 letter.")
    private String text;

}
