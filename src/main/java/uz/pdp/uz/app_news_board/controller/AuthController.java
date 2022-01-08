package uz.pdp.uz.app_news_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uz.app_news_board.payload.ApiResponse;
import uz.pdp.uz.app_news_board.payload.LoginDto;
import uz.pdp.uz.app_news_board.payload.RegisterDto;
import uz.pdp.uz.app_news_board.service.AuthService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.getIsSuccess() ? 201 : 409).body(apiResponse);

    }

    @PostMapping("/login")
    public HttpEntity<?>login(@RequestBody LoginDto loginDto){

        ApiResponse apiResponse =authService.login(loginDto);
        return ResponseEntity.status(apiResponse.getIsSuccess()?200:401).body(apiResponse);

    }


    /**
     * test after windows
     * @param ex
     * @return
     */


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
