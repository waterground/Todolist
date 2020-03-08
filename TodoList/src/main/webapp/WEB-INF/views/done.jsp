<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>완료 항목 보기 | Todo 리스트</title>
<link rel="stylesheet" href="/todo/resources/css/style.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="title">
		<h1>완료한 Todo 리스트</h1>
	</div>
	<div class="content">
		 <a class="btn btn-primary float-left" href="/todo/main?page=1&range=1">현재 목록 보기</a> <br>
		<br>
		<table class="table">
			<!-- ToDo 목록 -->
			<thead>
				<tr>
					<th style="width: 5%">#</th>
					<th style="width: 35%">할 일</th>
					<th style="width: 10%">이름</th>
					<th style="width: 7%">중요도</th>
					<th style="width: 10%">시작 날짜</th>
					<th style="width: 10%">목표 날짜</th>
					<th style="width: 14%">결과</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.id}</td>
						<td>${dto.title}</td>
						<td>${dto.name}</td>
						<td>${dto.priority}</td>
						<td>${dto.regDate}</td>
						<td>${dto.goalDate}</td>
						<td>
						<c:if test="${dto.result eq true}">
							<span style="font-weight:bold; color:blue;">성공</span>
						</c:if>
						<c:if test="${dto.result eq false}">
							<span style="font-weight:bold; color:red;">실패</span>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 페이징  -->
		<div class="text-center" style="margin:0 auto; width: 400px;">
			<ul class="pagination">
				<c:if test="${pagination.prev eq true}">
					<li class="page-item"><a class="page-link" href="#"
						onClick="prevEvent('${pagination.curPage}', '${pagination.curRange}', '${pagination.rangeSize}')">&lt;</a></li>
				</c:if>

				<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
					<li
						class="page-item <c:out value="${pagination.curPage == idx ? 'active' : ''}"/>"><a
						class="page-link" href="#"
						onClick="pageEvent('${idx}', '${pagination.curRange}', '${pagination.rangeSize}')">
							${idx} </a></li>
				</c:forEach>
				<c:if test="${pagination.next eq true}">
					<li class="page-item"><a class="page-link" href="#"
						onClick="nextEvent('${pagination.curRange}', '${pagination.rangeSize}')">&gt;</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<script>
	// 이전 버튼 이벤트
	function prevEvent(page, range, rangeSize) {
		var page = ((range - 1) * rangeSize);
		var range = range - 1;

		var url = "${cp}/done";
		url += "?page=" + page + "&range=" + range;

		location.href = url;
	}

	// 페이지 번호 버튼 이벤트
	function pageEvent(page, range, rangeSize) {
		var url = "${cp}/done";

		url += "?page=" + page + "&range=" + range;

		location.href = url;
	}

	// 다음 버튼 이벤트
	function nextEvent(range, rangeSize) {
		var page = parseInt(range * rangeSize) + 1;
		var range = parseInt(range) + 1;

		var url = "${cp}/done";
		url += "?page=" + page + "&range=" + range;

		location.href = url;
	}

	</script>
</body>
</html>