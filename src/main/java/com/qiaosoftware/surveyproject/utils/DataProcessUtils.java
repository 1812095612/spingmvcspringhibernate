package com.qiaosoftware.surveyproject.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.qiaosoftware.surveyproject.entity.manager.Auth;
import com.qiaosoftware.surveyproject.entity.manager.Res;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DataProcessUtils {
	
	/**
	 * 根据所有日志表的表名生成对应的子查询
	 * @param tableNames
	 * @return
	 */
	public static String generateSubSelect(List<String> tableNames) {
		
		StringBuilder builder = new StringBuilder();
		
		//SELECT * FROM `survey_log_2016_06` UNION SELECT * FROM `survey_log_2016_07` UNION SELECT * FROM `survey_log_2016_08` UNION SELECT * FROM `survey_log_2016_09`
		for (String tableName : tableNames) {
			
			builder.append("SELECT * FROM ").append(tableName).append(" UNION ");
			
		}
		
		return builder.substring(0, builder.lastIndexOf(" UNION "));
	}
	
	/**
	 * 根据偏移量生成数据库表的表名
	 * @param offset
	 * 		-2：上上个月
	 * 		-1：上一个月
	 * 		0：当前月
	 * 		1：下一个月
	 * 		2：下下一个月
	 * @return
	 * 		SURVEY_LOG_2016_05
	 */
	public static String generateTableName(int offset) {
		
		//1.获取日历对象
		Calendar instance = Calendar.getInstance();
		
		//2.在月份上附加偏移量
		instance.add(Calendar.MONTH, offset);
		
		//3.从日历对象中获取Date对象
		Date date = instance.getTime();
		
		//4.格式化Date对象生成表名
		return "SURVEY_LOG_"+new SimpleDateFormat("yyyy_MM").format(date);
		
		//System.out.println(offset+" "+year+" "+month);
		
		/*instance.add(Calendar.MONTH, offset);
		
		year = instance.get(Calendar.YEAR);
		
		month = instance.get(Calendar.MONTH);
		
		System.out.println(year+" "+month);*/
		
	}
	
	/**
	 * 验证某个用户是否可以访问某个资源
	 * @param res 资源对象
	 * @param resCodeArr 用户的权限码数组
	 * @return
	 * 		true：可以访问
	 * 		false：不可以访问
	 */
	public static boolean checkAuthority(Res res, String resCodeArr) {
		
		//1.从Res对象中获取权限码和权限位的值
		Integer resCode = res.getResCode();
		Integer resPos = res.getResPos();
		
		//2.将resCodeArr拆分成数组
		String[] codeArr = resCodeArr.split(",");
		
		//3.以权限位为下标，从数组中获取对应的元素
		String codeStr = codeArr[resPos];
		Integer code = Integer.parseInt(codeStr);
		
		//4.与运算
		int result = resCode & code;
		
		return result != 0;
	}
	
	/**
	 * 针对REST风格的URL地址特殊处理，将后面附着的数值去掉
	 * /guest/bag/removeBag/5/23
	 * /guest/survey/removeSurvey/23
	 * /guest/survey/showList
	 * @param servletPath
	 * @return
	 */
	public static String cutServletPath(String servletPath) {
		
		//设定如下循环：只要最后一节可以转换为整型就继续切割，直到无法转换时跳出循环
		while(true) {
			//1.查找servletPath中最后一个斜杠的位置
			int slantIndex = servletPath.lastIndexOf("/");
			
			//2.查找最后一节URL地址对应的子字符串
			String lastPart = servletPath.substring(slantIndex + 1);
			//System.out.println(lastPart);
			//3.检查最后一节字符串是否可转换成整型
			try{
				Integer.parseInt(lastPart);
				
				//4.说明最后一段URL地址是要去掉的内容
				servletPath = servletPath.substring(0, slantIndex);
				
			}catch(NumberFormatException e) {
				break ;
			}
			
		}
		
		return servletPath;
	}
	
	/**
	 * 深度复制
	 * @param sourceObj 源对象
	 * @return 目标对象
	 */
	public static Serializable deeplyCopy(Serializable sourceObj) {
		
		Serializable targetObj = null;
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		
		try {
			
			//创建字节数组输出流：不需要任何其他参数
			baos = new ByteArrayOutputStream();
			
			//根据字节数组输出流创建对象输出流
			oos = new ObjectOutputStream(baos);
			
			//将源对象写入到输出流中，最终会被写入到一个字节数组
			oos.writeObject(sourceObj);
			
			//获取字节数组
			byte[] byteArray = baos.toByteArray();
			
			//根据字节数组创建字节数组输入流
			bais = new ByteArrayInputStream(byteArray);
			
			//根据字节数组输入流创建对象输入流
			ois = new ObjectInputStream(bais);
			
			//从对象输入流中读取对象
			targetObj = (Serializable) ois.readObject();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return targetObj;
	}
	
	/**
	 * 将图片压缩按本来的长宽比例压缩为100宽度的jpg图片
	 * @param inputStream
	 * @param realPath /surveyLogos目录的真实路径，后面没有斜杠
	 * @return 将生成的文件路径返回 surveyLogos/4198393905112.jpg
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {
		
		OutputStream out = null;
		
		try {
			//1.构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			//2.获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();
			
			//3.计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;
			
			if(sourceWidth > 100) {
				//按比例压缩目标图片的尺寸
				targetWidth = 100;
				targetHeight = sourceHeight/(sourceWidth/100);
				
			}
			
			//4.创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			
			//5.绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
			
			//6.构造目标图片文件名
			String targetFileName = System.nanoTime() + ".jpg";
			
			//7.创建目标图片对应的输出流
			out = new FileOutputStream(realPath+"/"+targetFileName);
			
			//8.获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			
			//9.JPEG编码
			encoder.encode(targetImage);
			
			//10.返回文件名
			return "surveyLogos/"+targetFileName;
			
		} catch (Exception e) {
			
			return null;
		} finally {
			//10.关闭流
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	
	/**
	 * 将源字符串加密为密文，不可逆
	 * @param source
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(String source) {

		if(source == null || source.length() == 0) {
			return source;
		}
		
		//获取源字符串对应的字节数组
		byte[] sourceByte = source.getBytes();
		
		//获取加密工具
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//将源字符串对应的字节数组加密为固定长度的字节数组
		byte[] byteArr = digest.digest(sourceByte);
		
		//System.out.println(byteArr.length);
		
		//将加密后的字节数组转换为16进制字符组成的字符串
		char [] codeArr = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		//让每一个byteArr数组中的整数和15做与运算，就可以得到低四位对应的数值
		//让每一个byteArr数组中的整数先右移4伟，然后和15做与运算，就可以得到高四位对应的数值
		//不管高四位还是低四位取得的数值都正好可以作为codeArr数组的下标，那么以此得到的字符就是加密后的结果
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < byteArr.length; i++) {
			byte b = byteArr[i];
			//计算低四位
			int lowNum = b & 15;
			
			//计算高四位
			int highNum = (b >> 4) & 15;
			
			char lowChar = codeArr[lowNum];
			char highChar = codeArr[highNum];
			
			builder.append(highChar).append(lowChar);
		}
		
		return builder.toString();
	}


	/**
	 * 检查调整包裹顺序时包裹序号是否正确
	 * 利用Java的Set集合不允许重复的特性来进行验证。
	 * @param bagOrderList
	 * @return
	 */
	public static boolean checkBagOrder(List<Integer> bagOrderList) {
		
		Set<Integer> bagOrderSet = new HashSet<Integer>(bagOrderList);
		
		return bagOrderSet.size() == bagOrderList.size();
	}

	/**
	 * 根据submit_前缀查找提交按钮的名称
	 * @param param
	 * @return
	 */
	public static String getSubmitName(Map<String,String[]> param) {
		
		Set<String> keySet = param.keySet();
		
		for (String key : keySet) {
			
			boolean startsWith = key.startsWith("submit_");
			
			if(startsWith) {
				return key;
			}
			
		}
		
		return null;
	}

	/**
	 * 手动合并答案
	 * @param allBagMap
	 * @param bagId
	 * @param param
	 */
	public static void mergeParam(
			Map<Integer, Map<String, String[]>> allBagMap, Integer bagId,
			Map<String, String[]> param) {
		
		if(param == null || param.size() == 0) {
			return ;
		}
		
		if(allBagMap == null) {
			return ;
		}
		
		Map<String, String[]> newParam = new HashMap<String, String[]>(param);
		
		//遍历旧的param，将所有的值逐个保存到新的Map中
		/*Set<String> keySet = param.keySet();
		
		for (String key : keySet) {
			
			String[] value = param.get(key);
			
			newParam.put(key, value);
			
		}*/
		
		allBagMap.put(bagId, newParam);
		
	}

	/**
	 * 将字符串数组转换为字符串
	 * [1,2,3]
	 * 1,2,3
	 * @param paramValues
	 * @return
	 */
	public static String convertStringArr(String[] paramValues) {
		
		if(paramValues == null || paramValues.length == 0) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < paramValues.length; i++) {
			String paramValue = paramValues[i];
			builder.append(paramValue).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}

	/**
	 * 将整型数组转换为字符串
	 * @param codeArr
	 * @return
	 */
	public static String converIntArr(int[] codeArr) {
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < codeArr.length; i++) {
			int code = codeArr[i];
			builder.append(code).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}

	/**
	 * 根据角色集合计算权限码数组，不论Admin还是User都适用
	 * @param roleSet
	 * @param maxPos
	 * @return
	 */
	public static int[] calculateCodeArr(Set<Role> roleSet, Integer maxPos) {
		
		//1.声明一个保存权限码的数组
		int [] codeArr = new int[maxPos+1];
		
		//2.遍历集合数据
		for (Role role : roleSet) {
			
			Set<Auth> authSet = role.getAuthSet();
			
			for (Auth auth : authSet) {
				
				Set<Res> resSet = auth.getResSet();
				
				for (Res res : resSet) {
					
					//3.获取当前资源的权限码和权限位
					Integer resCode = res.getResCode();
					Integer resPos = res.getResPos();
					
					//4.以权限位作为数组下标访问codeArr，获取数组中执行“或运算”之前的旧值
					int oldValue = codeArr[resPos];
					
					//5.将旧值和resCode做“或运算”得到新值
					int newValue = oldValue | resCode;
					
					//6.将新值保存回数组的原来位置
					codeArr[resPos] = newValue;
					
				}
				
			}
			
		}
		
		return codeArr;
	}

}
































