package uz.pdp.uz.app_news_board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.uz.app_news_board.entity.User;
import uz.pdp.uz.app_news_board.repository.UserRepository;
import uz.pdp.uz.app_news_board.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            //authorization = authorization.substring(7);
            String token=authorization.substring(7);
            //System.out.println(authorization);
            System.out.println(token);
            String login = jwtProvider.getEmailFromToken(token);
            if (login != null) {

                UserDetails userDetails = authService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Sistemaga shu user kirdi deb set qilindi
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }else if (authorization != null && authorization.startsWith("Basic")) {
            String[] cardLoginAndPassword = getCardLoginAndPassword(authorization);


            String login =cardLoginAndPassword[0];

            String password=cardLoginAndPassword[1];

            UserDetails userDetails =authService.loadUserByLogin(login);

            if (userDetails==null)
                System.out.println("null");


            if (passwordEncoder.matches(password,userDetails.getPassword())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                                                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("password xato.");
            }
        }

        //Spring ni ozini filtiri Biz yozgan filtrlaga tushmasa o'zinikiga tushadi
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    public String[] getCardLoginAndPassword(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":");

    }
}
