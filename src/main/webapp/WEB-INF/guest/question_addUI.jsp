<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		//页面刚加载时先将选项所在的行隐藏
		$("#optionTr").hide();
		
		//单击题型单选按钮时切换“选项行”显示状态
		$(":radio").click(function(){
			
			//检查当前点击的是“单/多选”还是“简答”
			var type = this.value;
			
			if(type == 2) {
				
				//简答题
				$("#optionTr").hide();
				
			}else{
				
				//单/多选题
				$("#optionTr").show();
				
			}
			
		});
		
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[创建问题]
		<form action="guest/question/saveQuestion" method="post">
		
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<input type="hidden" name="bag.bagId" value="${bagId }"/>
			
			<table class="formTable">
				
				<tr>
					<td>问题题干</td>
					<td>
						<input type="text" name="questionName" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>选择题型</td>
					<td>
						<input type="radio" id="type01" name="questionType" value="0"/>
						<label for="type01">单选题</label>
						
						<input type="radio" id="type02" name="questionType" value="1"/>
						<label for="type02">多选题</label>
						
						<input type="radio" id="type03" name="questionType" value="2" checked="checked"/>
						<label for="type03">简答题</label>
					</td>
				</tr>
				
				<tr id="optionTr">
					<td>选项</td>
					<td>
						<textarea name="options" class="multiInput"></textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>