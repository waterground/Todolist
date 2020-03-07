<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>할 일 추가 | Todo 리스트</title>
<link rel="stylesheet" href="/todo/resources/css/style.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="title">
		<h1>할 일이 무엇인가요?</h1>
	</div>
	<div class="content">
		<form:form action="${cp}/write" method="post" modelAttribute="dto">
			<div class="form-group">
				<form:label path="title">할 일 </form:label> 
				<form:input path="title" class="form-control"/>
			</div>
			<div class="form-group">
				<form:label path="name">이름 </form:label>
				<form:input path="name" class="form-control"/>
			</div>
			<div class="form-group">
				<form:label path="priority">중요도</form:label><br/>
				<form:radiobutton path="priority" value="상" label="상" checked="true"/>
				<form:radiobutton path="priority" value="중" label="중"/>
				<form:radiobutton path="priority" value="히" label="하"/>
			</div>
			<div class="form-group">
				<form:label path="goalDate">목표 날짜</form:label>
				<form:input id="datepicker" path="goalDate"/>
			</div>
			<br> 
			<button class="btn btn-primary float-right">완료</button>
		</form:form>
	</div>
	<script>
	$(function(){
		$("#datepicker").datepicker({
			dateFormat: 'yy-mm-dd' //Input Display Format 변경
			,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
		});
		
		//초기값을 오늘 날짜로 설정
        $('#datepicker').datepicker('setDate', 'today');

	});
</script>
</body>
</html>