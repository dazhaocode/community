package com.alan.community.advice;

import com.alan.community.dto.ResultDTO;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author alan
 * @date 2020/3/21 15:36
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable ex, Model model,HttpServletRequest request,HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDTO resultDTO;
        if (contentType!=null&& contentType.equals("application/json")) {
            //返回json
            //这个地方存在一定不需要成分 待思考。。。。
            if (ex instanceof CustomizeException) {
                 resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                 resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            //页面跳转
            if (ex instanceof CustomizeException)
                model.addAttribute("msg", ((CustomizeException) ex).getMsg());
            else
                model.addAttribute("msg", CustomizeErrorCode.SYS_ERROR);
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}



