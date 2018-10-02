package com.qiaosoftware.surveyproject.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadDownloadUtils {

    /**
     * 工程中/webapp/uploadfiledir目录。需要将这个虚拟路径转换为真实路径
     */
    private static String virtualPath = "/uploadfiledir";

    /**
     * 文件上传示例
     * @param session
     * @param uploadFile
     * @return 返回存放在服务器上的文件的绝对地址
     */
    public static String fileUploadDemo(HttpSession session, MultipartFile uploadFile) {

        if (!uploadFile.isEmpty()) {
            // 1.文件上传项相关的数据
            // [1]原始文件名
            String originalFilename = uploadFile.getOriginalFilename();
            System.out.println("originalFilename=" + originalFilename);

            // [2]文件大小
            long size = uploadFile.getSize();
            System.out.println("size=" + size);

            // [3]文件的内容类型
            String contentType = uploadFile.getContentType();
            System.out.println("contentType=" + contentType);

            // 2.根据文件上传的信息进行验证
            // ①进行文件大小的验证10M
            if (size > 1024 * 1024 * 10) {
                return "FileTooLarge";
            }

            // ②进行文件类型的验证
            if (!GlobalValues.OTHER_TYPES.contains(contentType)) {
                return "FileTypeNotAllowed";
            }

            // 3.将上传的图片保存到服务器端的指定位置
            // 转换：由于IO操作相关的API必须操作真实的物理路径，所以需要将虚拟路径进行转换
            // [1]获取ServletContext对象
            ServletContext servletContext = session.getServletContext();

            // [2]调用getRealPath方法
            String realPath = servletContext.getRealPath(virtualPath);
            System.out.println(realPath);
            // 如果路径下该文件夹不存在，则创建文件夹
            createDirectory(realPath);

            String tempname = randomUUID32()
                    + originalFilename.substring(
                            originalFilename.lastIndexOf("."),
                            originalFilename.length());
            FileOutputStream outF = null;
            try {
                outF = new FileOutputStream(realPath + File.separator + tempname);
                // 写入文件
                outF.write(uploadFile.getBytes());
                outF.flush();

                return realPath + File.separator + tempname;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outF != null) {
                    try {
                        outF.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return null;
    }
    
    /**
     * 文件下载示例
     * @param request
     * @param response
     * @throws IOException
     */
    public static void fileDownloadDemo(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //1.从请求参数中获取文件名
        String fileName = request.getParameter("fileName");
        System.out.println("fileName="+fileName);
        
        //2.根据文件名创建输入流对象
        //①创建虚拟路径
        //②转换为真实路径
        String realPath = request.getSession().getServletContext().getRealPath(virtualPath + File.separator + fileName);
        
        //③创建输入流对象
        InputStream in = new FileInputStream(realPath);
        
        //======================检测当前浏览器是否是火狐===================
        String userAgent = request.getHeader("User-Agent");
        
        //======================设置下载专用的响应消息头=========================
        //设置响应消息头，告诉浏览器当前响应是一个下载文件
        response.setContentType("application/x-msdownload");
        //考虑到文件名中可能包含非ASCII码字符，所以应该进行编码后再传输
        if(userAgent.contains("Firefox")) {
            //如果是火狐浏览器，进行特殊设置
            //fileName = "=?utf-8?B?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
        }else{
            //如果不是火狐，则正常编码
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        //告诉浏览器：当前响应数据要求用户干预并保存到文件中，以及文件名
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        //======================设置下载专用的响应消息头=========================
        
        //3.通过response对象获取能够给浏览器返回响应的输出流对象
        ServletOutputStream out = response.getOutputStream();
        
        //4.返回响应数据
        returnResponseDatas(in, out);
    }
    
    /**
     * 解析Excel文件的内容
     * @param fileRealPath
     * @param fileName
     * @param index
     * @throws IOException
     */
    public static void parseExcelFileDemo(String fileRealPath, String fileName, int index) throws IOException {
        Sheet sheet = readSheet(fileRealPath, fileName, index);
        //循环遍历看看
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell != null && cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
                    System.out.print(cell.toString() + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("******************************");
        int lastRowNum = sheet.getLastRowNum() + 1;
        System.out.println("lastRowNum："+lastRowNum);
        //第二种方式
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }
    }
    
    /**
     * 读取Excel文件
     * @param fileRealPath  文件绝对地址
     * @param fileName      文件名
     * @param index         读取哪个sheet，页签
     * @return
     * @throws IOException
     */
    private static Sheet readSheet(String fileRealPath, String fileName, int index) throws IOException {
        boolean isE2007 = false; // 判断是否是excel2007格式
        if (fileName.endsWith("xlsx")) {
            isE2007 = true;
        }
        InputStream fis = new FileInputStream(fileRealPath); // 建立输入流
        Workbook wb = null;
        // 根据文件格式(2003或者2007)来初始化
        if (isE2007) {
            wb = new XSSFWorkbook(fis);
        } else {
            wb = new HSSFWorkbook(fis);
        }
        int numberOfSheets = wb.getNumberOfSheets();
        System.out.println("numberOfSheets:"+numberOfSheets);
        Sheet sheet = wb.getSheetAt(index); // 获得第几个表单,index从0开始
        return sheet;
    }
    
    /**
     * 相对路径转绝对路径
     * @param request
     * @param path
     * @return
     */
    public static String convertAbsolutePath(HttpServletRequest request, String path) {
        String realPath = request.getSession().getServletContext().getRealPath(path);
        return realPath;
    }

    /**
     * 创建文件路径
     * 
     * @param path
     */
    public static void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取32位UUID字符串
     * 
     * @return
     */
    public static String randomUUID32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 返回响应数据使用缓冲流加速
     * @param in
     * @param out
     */
    public static void returnResponseDatas(InputStream in, OutputStream out){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(out);
            
            byte[] b = new byte[1024];
            int len = 0;
            while((len = bis.read(b)) != -1){
                bos.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }


}
