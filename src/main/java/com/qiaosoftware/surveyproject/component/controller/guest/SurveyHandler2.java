package com.qiaosoftware.surveyproject.component.controller.guest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qiaosoftware.surveyproject.component.service.SurveyService;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.exception.FileTooLargeExceptionForEdit;
import com.qiaosoftware.surveyproject.exception.FileTooLargeExceptionForSave;
import com.qiaosoftware.surveyproject.exception.FileTypeNotAllowedExceptionForEdit;
import com.qiaosoftware.surveyproject.exception.FileTypeNotAllowedExceptionForSave;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;
import com.qiaosoftware.surveyproject.utils.GlobalValues;

public class SurveyHandler2 {
	
	@Autowired
	private SurveyService surveyService;
	
	@ModelAttribute
	public Survey getSurvey(@RequestParam(value="surveyId", required=false) Integer surveyId) {
		
		if(surveyId == null) {
			//没有ID时需要创建空的对象，否则SpringMVC就会将null值通过Survey survey参数传给saveSurvey()方法
			//导致空指针异常
			return new Survey();
		}else{
			return surveyService.getEntity(surveyId);
		}
	}
	
	@RequestMapping("/guest/survey/updateSurvey")
	public String updateSurvey(HttpServletRequest request, HttpSession session, Survey survey, @RequestParam("logoFile") MultipartFile logoFile) throws IOException {
		
		//1.检查是否上传了文件
		if(!logoFile.isEmpty()) {
			
			//2.检查文件大小是否符合要求
			long size = logoFile.getSize();
			
			if(size > 1024*500) {
				
				request.setAttribute("survey", survey);
				
				throw new FileTooLargeExceptionForEdit();
			}
			
			//3.检查文件类型是否符合要求
			String contentType = logoFile.getContentType();
			
			if(!GlobalValues.ALLOWD_TYPES.contains(contentType)) {
				
				request.setAttribute("survey", survey);
				
				throw new FileTypeNotAllowedExceptionForEdit();
			}
			
			String virtualPath = "/surveyLogos";
			
			ServletContext servletContext = session.getServletContext();
			
			String realPath = servletContext.getRealPath(virtualPath);
			
			InputStream inputStream = logoFile.getInputStream();
			
			String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
			
			survey.setLogoPath(logoPath);
			
		}
		
		//4.没有上传文件，则正常更新Survey对象
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		survey.setUser(user);
		
		//6.保存Survey对象
		surveyService.updateEntity(survey);
		
		//return "redirect:/guest/survey/showMyUncompletedSurvey?pageNoStr="+Integer.MAX_VALUE;
		return "redirect:/guest/survey/showMyUncompletedSurvey";
		
	}
	
	@RequestMapping("/guest/survey/editSurvey/{surveyId}")
	public String editSurvey(@PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		Survey survey = surveyService.getEntity(surveyId);
		map.put("survey", survey);
		
		return "guest/survey_editUI";
	}
	
	@RequestMapping("/guest/survey/removeSurvey/{surveyId}")
	public void removeSurvey(@PathVariable("surveyId") Integer surveyId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		surveyService.removeEntityById(surveyId);
		
		//1.获取当前请求发起时所在的页面的URL地址：从哪里来？
		String referer = request.getHeader("Referer");
		
		//2.重定向到这个地址：到哪里去？
		response.sendRedirect(referer);
		
	}
	
	/*
	用这种方式返回页面会进入第1页，而不是原来所在的页
	@RequestMapping("/guest/survey/removeSurvey/{surveyId}")
	public String removeSurvey(@PathVariable("surveyId") Integer surveyId) {
		
		surveyService.removeEntityById(surveyId);
		
		return "redirect:/guest/survey/showMyUncompletedSurvey";
	}*/
	
	@RequestMapping("/guest/survey/showMyUncompletedSurvey")
	public String showMyUncompletedSurvey(
			HttpSession session, 
			Map<String, Object> map, 
			@RequestParam(value="pageNoStr", required=false) String pageNoStr) {
		
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		Integer userId = user.getUserId();
		
		Page<Survey> page = surveyService.getMyUncompletedSurvey(pageNoStr, Page.PAGE_SIZE_SMALL, userId);
		map.put(GlobalNames.PAGE, page);
	
		return "guest/survey_uncompleted";
	}
	
	@RequestMapping("/guest/survey/saveSurvey")
	public String saveSurvey(HttpServletRequest request, HttpSession session, Survey survey, @RequestParam("logoFile") MultipartFile logoFile) throws IOException {
		
		if(!logoFile.isEmpty()) {
			//1.获取表单提交的信息
			//①普通表单项
			String surveyName = survey.getSurveyName();
			System.out.println("surveyName="+surveyName);
			
			//②文件上传项相关的数据
			//[1]原始文件名
			String originalFilename = logoFile.getOriginalFilename();
			System.out.println("originalFilename="+originalFilename);
			
			//[2]文件大小
			long size = logoFile.getSize();
			System.out.println("size="+size);
			
			//[3]文件的内容类型
			String contentType = logoFile.getContentType();
			System.out.println("contentType="+contentType);
			
			//2.根据文件上传的信息进行验证
			//①进行文件大小的验证
			if(size > 1024*500) {
				
				request.setAttribute("survey", survey);
				
				throw new FileTooLargeExceptionForSave();
				
			}
			
			//②进行文件类型的验证
			if(!GlobalValues.ALLOWD_TYPES.contains(contentType)) {
				
				request.setAttribute("survey", survey);
				
				throw new FileTypeNotAllowedExceptionForSave();
			}
			
			//3.将上传的图片保存到服务器端的指定位置
			//①指定位置：工程中/webapp/surveyLogos目录。需要将这个虚拟路径转换为真实路径
			String virtualPath = "/surveyLogos";
			
			//②转换：由于IO操作相关的API必须操作真实的物理路径，所以需要将虚拟路径进行转换
			//[1]获取ServletContext对象
			ServletContext servletContext = session.getServletContext();
			
			//[2]调用getRealPath方法
			String realPath = servletContext.getRealPath(virtualPath);
			
			//surveyLogos目录在项目真实的部署目录下的物理路径
			//D:\WorkSpaceYou\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Survey_1_UI\surveyLogos
			System.out.println(realPath);
			
			//[3]从logoFile对象中获取文件的输入流对象
			InputStream inputStream = logoFile.getInputStream();
			
			//③调用工具方法压缩图片并保存到surveyLogos目录下
			String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
			System.out.println("logoPath="+logoPath);
			
			//4.设置logoPath
			//注意：必须在上传图片真的有数据的情况下才设置logoPath，否则保持其默认值
			survey.setLogoPath(logoPath);
			
		}
		
		//5.从Session域中读取当前登录的User对象
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		survey.setUser(user);
		
		//6.保存Survey对象
		surveyService.saveEntity(survey);
		
		//return "redirect:/guest/survey/showMyUncompletedSurvey?pageNoStr="+Integer.MAX_VALUE;
		return "redirect:/guest/survey/showMyUncompletedSurvey";
	}
	
	@RequestMapping("/guest/survey/toAddUI")
	public String toAddUI(Map<String, Object> map) {
		
		Survey survey = new Survey();
		map.put("survey", survey);
		
		return "guest/survey_addUI";
	}

    @RequestMapping("/guest/survey/abc")
    public void abc() {

    }

}
