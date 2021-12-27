package uz.pdp.uz.app_news_board.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String massage;
    private Boolean isSuccess;
    private Object object;

    public ApiResponse(String massage, Boolean isSuccess) {
        this.massage = massage;
        this.isSuccess = isSuccess;
    }
}
