<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a href="${pageContext.request.contextPath }/manager/showList">Show Emp List</a>

	<form action="${pageContext.request.contextPath }/manager/testFileUpload"
		method="post" enctype="multipart/form-data">

		普通数据：<input type="text" name="userName" /><br />
		文件数据：<input type="file" name="uploadFile" /><br />
		<input type="submit" value="上传" />

	</form>
	
	<a href="${pageContext.request.contextPath }/manager/testFileDownload?fileName=专门测试用的.xlsx">测试下载</a>
	
	
	<form action="${pageContext.request.contextPath }/manager/testParseExcelFile"
	                       method="post" enctype="multipart/form-data">
	
		普通数据：<input type="text" name="userName" /><br />
		文件数据：<input type="file" name="uploadFile" /><br />
		<input type="submit" value="上传" />
	
	</form>

</body>
</html>