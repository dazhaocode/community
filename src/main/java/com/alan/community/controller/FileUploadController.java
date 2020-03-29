package com.alan.community.controller;

import com.alan.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alan
 * @date 2020/3/28 19:47
 */
@Controller
public class FileUploadController {
    @RequestMapping("file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
            fileDTO.setUrl("/images/test.jpg");
        return fileDTO;
    }

}
