package com.kenIT.controller;

import com.kenIT.model.UploadFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class ControllerFile {

    @GetMapping("/one-file")
    public ModelAndView uploadFile() {
        ModelAndView modelAndView = new ModelAndView("upload-one-file");
        modelAndView.addObject("uploadFile", new UploadFile());
        return modelAndView;
    }

    @GetMapping("/multi-file")
    public ModelAndView upMultiFile() {
        ModelAndView modelAndView = new ModelAndView("upload-multi-file");
        modelAndView.addObject("uploadFile", new UploadFile());
        return modelAndView;
    }

    @PostMapping("/one-file")
    public String uploadFile(HttpServletRequest request, Model model, @ModelAttribute("uploadFile") UploadFile uploadFile) {
        return this.doUpload(request, model, uploadFile);
    }

    @PostMapping("/multi-file")g
    public String uploadMultiFiles(HttpServletRequest request, Model model, @ModelAttribute("uploadFile") UploadFile uploadFile) {
        return this.doUpload(request, model, uploadFile);
    }

    private String doUpload(HttpServletRequest request, Model model, UploadFile uploadFile) {
        Map<File, String> uploadFiles = new HashMap<>();
        String uploadRootPath = request.getServletContext().getRealPath("uploads");
        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdir(); // tạo thư mục nếu chưa có
        }

        CommonsMultipartFile[] files = uploadFile.getFiles();
        for (CommonsMultipartFile file :
                files) {
            String name = file.getOriginalFilename();
            if (name != null && name.length() > 0) {
                try {
                    File writeFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(writeFile));
                    bufferedOutputStream.write(file.getBytes());
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    uploadFiles.put(writeFile, name); //insert sugar dẫn vào map
                    System.out.println("Write file:" + writeFile);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("description", uploadFile.getDescription());
        model.addAttribute("uploadedFiles", uploadFiles);
        return "upload-result";
    }
}
