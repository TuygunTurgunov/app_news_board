package uz.pdp.uz.app_news_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uz.app_news_board.entity.News;
import uz.pdp.uz.app_news_board.payload.ApiResponse;
import uz.pdp.uz.app_news_board.payload.NewsAddDto;
import uz.pdp.uz.app_news_board.service.NewsService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    /**
     * Post for users
     * @param newsAddDto =>DTO
     * @return => APIResponse
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody NewsAddDto newsAddDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiResponse apiResponse = newsService.add(newsAddDto, authentication);

        return ResponseEntity.status(apiResponse.getIsSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Get news for users and admin by role
     * @return List
     */
    @GetMapping
    public ResponseEntity<?> getALl(@RequestParam int page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Pageable pageable=PageRequest.of(page,15);
        List<News> newsList=newsService.getAll(authentication);
        final int start =(int) pageable.getOffset();
        final int end = Math.min((start+pageable.getPageSize()),newsList.size());
        final Page<News>newsPage=new PageImpl<>(newsList.subList(start,end),pageable,newsList.size());

        return ResponseEntity.ok(newsPage);

    }

    /**
     * edit by user
     * @param id UUID
     * @param newsAddDto DTO
     * @return APIResponse
     */

    @PatchMapping("/edit/byUser/{id}")
    public ResponseEntity<?>editByUser(@PathVariable UUID id ,@Valid @RequestBody NewsAddDto newsAddDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiResponse apiResponse = newsService.editByUser(id,newsAddDto,authentication);
        return ResponseEntity.status(apiResponse.getIsSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * Check mews by Admin  true/false    and status of news
     * @param id =>  UUID
     * @return ApiResponse
     */
    @PatchMapping("/edit/checkedByAdmin/{id}")
    public ResponseEntity<?> editCheckedByAdmin(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiResponse apiResponse=newsService.checkedByAdmin(id,authentication);
        return ResponseEntity.status(apiResponse.getIsSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Cancel news by Admin true/false and status news
     * @param id=>UUID
     * @return ApiResponse
     */
    @PatchMapping("/edit/canceledByAdmin/{id}")
    public ResponseEntity<?>editCanceledByAdmin(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiResponse apiResponse=newsService.cancelByAdmin(id,authentication);
        return ResponseEntity.status(apiResponse.getIsSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
  }


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



    /**
     * Swagger
     */

//    @ExceptionHandler(ConversionFailedException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleConnversion(RuntimeException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    //@ExceptionHandler(BookNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleBookNotFound(RuntimeException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }


}
