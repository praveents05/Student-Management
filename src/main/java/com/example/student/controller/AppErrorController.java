package com.example.student.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String message = "Something went wrong!";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                message = "Page Not Found (404)";
            } else if (statusCode == 403) {
                message = "Access Denied (403)";
            } else if (statusCode == 500) {
                message = "Internal Server Error (500)";
            }
        }

        model.addAttribute("errorMessage", message);
        return "error";   // error.html inside templates
    }
}