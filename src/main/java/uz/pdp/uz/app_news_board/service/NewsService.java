package uz.pdp.uz.app_news_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.pdp.uz.app_news_board.entity.News;
import uz.pdp.uz.app_news_board.entity.Role;
import uz.pdp.uz.app_news_board.entity.Status;
import uz.pdp.uz.app_news_board.entity.User;
import uz.pdp.uz.app_news_board.entity.enums.RoleName;
import uz.pdp.uz.app_news_board.entity.enums.StatusName;
import uz.pdp.uz.app_news_board.payload.ApiResponse;
import uz.pdp.uz.app_news_board.payload.NewsAddDto;
import uz.pdp.uz.app_news_board.repository.NewsRepository;
import uz.pdp.uz.app_news_board.repository.StatusRepository;

import java.sql.Timestamp;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    StatusRepository statusRepository;


    public ApiResponse add(NewsAddDto newsAddDto, Authentication authentication){

//1
        User currentUser=(User) authentication.getPrincipal();
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals(RoleName.ROLE_ADMIN.name()))
                return new ApiResponse("Admin cannot add news",false);
        }

//2
        News news=new News();
        news.setNewsHeader(newsAddDto.getNewsHeader());
        news.setText(newsAddDto.getText());
        news.setNewsOwner(currentUser);
        news.setNewsOwnerLogin(currentUser.getLogin());
        news.setNewsOwnerEmail(currentUser.getEmail());
        Optional<Status> optionalStatus = statusRepository.findByStatusName(StatusName.STATUS_NEW);
        optionalStatus.ifPresent(news::setStatusName);

        newsRepository.save(news);

        return new ApiResponse("news has added but not checked yet by admin ",true);


    }


    public List<News> getAll(Authentication authentication) {
        boolean isAdmin=false;
        User currentUser =(User) authentication.getPrincipal();
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals(RoleName.ROLE_ADMIN.name()))
                isAdmin=true;
        }


        if (!isAdmin){
            return getAllForUser(currentUser);
        }

        return newsRepository.findAll();
    }


    public List<News> getAllForUser(User user){

        List<News> allByCheckedByAdminAndCancel = newsRepository.findAllByCheckedByAdminAndCancelAndNewsOwnerNot(true,false,user);
        List<News> allByNewsOwner = newsRepository.findAllByNewsOwner(user);
        List<News>mixed=new ArrayList<>();
        mixed.addAll(allByCheckedByAdminAndCancel);
        mixed.addAll(allByNewsOwner);
        return mixed;

 }


    public ApiResponse checkedByAdmin(UUID id, Authentication authentication) {
        User currentUser =(User) authentication.getPrincipal();
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals(RoleName.ROLE_USER.name()))
                return new ApiResponse("user cannot check news",false);
        }

        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent())
            return new ApiResponse("news not found",false);

        News editingNews = optionalNews.get();
        editingNews.setCheckedByAdmin(true);
        editingNews.setCancel(false);
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        editingNews.setTimeCheckedByAdmin(timestamp);
        Optional<Status> optionalStatus = statusRepository.findByStatusName(StatusName.STATUS_NOT_NEW);
        optionalStatus.ifPresent(editingNews::setStatusName);
        newsRepository.save(editingNews);
        return new ApiResponse("News checked",true);
    }

    public ApiResponse cancelByAdmin(UUID id, Authentication authentication) {
        User currentUser =(User) authentication.getPrincipal();
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals(RoleName.ROLE_USER.name()))
                return new ApiResponse("user cannot canceled news",false);
        }

        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent())
            return new ApiResponse("news not found",false);

        News editingNews = optionalNews.get();
        editingNews.setCheckedByAdmin(false);
        editingNews.setCancel(true);
        //Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        editingNews.setTimeCheckedByAdmin(null);
        Optional<Status> optionalStatus = statusRepository.findByStatusName(StatusName.STATUS_NEW);
        optionalStatus.ifPresent(editingNews::setStatusName);
        newsRepository.save(editingNews);
        return new ApiResponse("News  canceled",true);

    }


    public ApiResponse editByUser(UUID id, NewsAddDto newsAddDto, Authentication authentication) {
        User currentUser =(User) authentication.getPrincipal();
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals(RoleName.ROLE_ADMIN.name()))
                return new ApiResponse("news text only user can edit",false);
        }

        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent())
            return new ApiResponse("news not fond by id",false);

        News editingNews = optionalNews.get();
        User newsOwner = editingNews.getNewsOwner();
        if (!newsOwner.equals(currentUser))
            return new ApiResponse("only news owner can edit news text",false);

        editingNews.setNewsHeader(newsAddDto.getNewsHeader());
        editingNews.setText(newsAddDto.getText());
        editingNews.setCheckedByAdmin(false);
        editingNews.setCancel(true);
        editingNews.setTimeCheckedByAdmin(null);
        editingNews.setTimeCheckedByAdmin(null);
        Optional<Status> optionalStatus = statusRepository.findByStatusName(StatusName.STATUS_NEW);
        optionalStatus.ifPresent(editingNews::setStatusName);

        newsRepository.save(editingNews);
        return new ApiResponse("news edited",true);




    }
}
