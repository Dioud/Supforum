package com.supinfo.rmt.filter;

import com.supinfo.rmt.controller.UserController;
import com.supinfo.rmt.entity.User;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter(urlPatterns = {"/auth/manage.xhtml"})
public class AdministratorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


            UserController userController
                    = (UserController) httpRequest.getSession().getAttribute("userController");

            User user = userController.getUser();

            if (user.getUsername() != null) {
                if (user.getRole().equals("administrator")) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            httpResponse.sendRedirect("/forum/login.xhtml");

       
    }

    @Override
    public void destroy() {

    }

}
