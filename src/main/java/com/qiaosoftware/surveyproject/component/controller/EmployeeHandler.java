package com.qiaosoftware.surveyproject.component.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qiaosoftware.surveyproject.component.service.EmployeeService;
import com.qiaosoftware.surveyproject.entity.guest.Employee;
import com.qiaosoftware.surveyproject.utils.FileUploadDownloadUtils;

@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeService employeeService;
    
    @RequestMapping("/manager/testParseExcelFile")
    public void testParseExcelFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
            @RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
        String returnedvalue = FileUploadDownloadUtils.fileUploadDemo(session, uploadFile);
        int lastIndexOf = returnedvalue.lastIndexOf(File.separator);
        String fileName = returnedvalue.substring(lastIndexOf + 1);
        System.out.println(fileName);
        FileUploadDownloadUtils.parseExcelFileDemo(returnedvalue, fileName, 0);
        
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(returnedvalue + "，请求参数：userName=" + request.getParameter("userName"));
    }

    @RequestMapping("/manager/testFileDownload")
    public void testFileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileUploadDownloadUtils.fileDownloadDemo(request, response);
    }
    
    @RequestMapping("/manager/testFileUpload")
    public void testFileUpload(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
            @RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
        String returnedvalue = FileUploadDownloadUtils.fileUploadDemo(session, uploadFile);
        
        
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(returnedvalue + "，请求参数：userName=" + request.getParameter("userName"));
    }

    @RequestMapping("/manager/showList")
    public String showList(Map<String, Object> map) {

        List<Employee> empList = employeeService.getEmpList();
        map.put("empList", empList);

        return "emp/showList";
    }

    public EmployeeHandler() {
        System.out.println("EmployeeHandler对象创建了！");
    }

}
