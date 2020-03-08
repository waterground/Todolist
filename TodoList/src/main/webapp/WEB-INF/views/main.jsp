<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>메인 | Todo 리스트</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="/todo/resources/css/style.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="title">
		<h1>Todo 리스트</h1>
	</div>
	<div class="content">
		<a href="/todo/writeForm" class="btn btn-primary float-right">추가</a> 
		<a class="btn btn-primary float-left" href="/todo/done?page=1&range=1">이전 목록 보기</a> <br>
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
					<th colspan="3">변경</th>
				</tr>
			</thead>
			<tbody id="todoList">
			<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.id}</td>
					<td>${dto.title}</td>
					<td>${dto.name}</td>
					<td>${dto.priority}</td>
					<td>${dto.regDate}</td>
					<td>${dto.goalDate}</td>
					<td><a class='btn btn-outline-primary' href='/todo/finish/${dto.id}'>완료</a></td>
					<td><a class='btn btn-outline-warning' href='#' data-toggle='modal' data-target='#modifyTodo' 
							data-id='${dto.id}' data-title='${dto.title}' data-name='${dto.name}'
							data-priority='${dto.priority}' data-goaldate='${dto.goalDate}'>수정</a></td>
					<td><a class='btn btn-outline-danger' href='/todo/delete/${dto.id}'>삭제</a></td>
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
	<!-- todo 수정 -->
	<div class="modal" id="modifyTodo" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">바꿀 내용을 입력하세요</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label for="uTitle"> 할 일 </label> 
							<input type="text" id="uTitle" name="uTitle" class="form-control">
						</div>
						<div class="form-group">
							<label for="uName"> 이름 </label> 
							<input type="text" id="uName" name="uName" class="form-control">
						</div>
						<div class="form-group">
							<label for="uPriority"> 중요도 </label><br> 
							<label class="radio-inline">상</label> 
							<input type="radio" name="uPriority" value="상"> 
							<label class="radio-inline">중</label>
							<input type="radio" name="uPriority" value="중"> 
							<label class="radio-inline">하</label> 
							<input type="radio" name="uPriority" value="하">
						</div>
						<div class="form-group">
							<label for="uGoalDate"> 목표 날짜 </label> 
							<input type="text" id="datepicker" name="uGoalDate"
								class="form-control">
						</div>
						<button class="btn btn-primary float-right" onclick="modify();">수정</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script> 
		var id = null;
		var today = new Date();
		
		$("#datepicker").datepicker({
			dateFormat: 'yy-mm-dd' //Input Display Format 변경
			,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: new Date(today.setDate(today.getDate() + 1)) // 최소 내일이 goalDate가 되도록 설정
		});
		
		$("#modifyTodo").on('show.bs.modal', function(event) {
			// 수정 전 값 받아오기
			id = $(event.relatedTarget).data('id');
			var titleVal = $(event.relatedTarget).data('title');
			var nameVal = $(event.relatedTarget).data('name');
			var priorityVal = $(event.relatedTarget).data('priority');
			var goalDateVal = $(event.relatedTarget).data('goaldate');
			
			// 수정 전 데이터를 폼에 체우기
			var title = $("#uTitle").val(titleVal);
			var name = $("#uName").val(nameVal);
			if(priorityVal == "상"){
				$('input:radio[name="uPriority"][value="상"]').prop('checked', true);
			}else if(priorityVal == "중"){
				 $('input:radio[name="uPriority"][value="중"]').prop('checked', true);
			}else{
				$('input:radio[name="uPriority"][value="하"]').prop('checked', true);
			}
	        $('#datepicker').datepicker('setDate', goalDateVal);
			
		});

		function modify(){
			var titleVal = $("#uTitle").val();
			var nameVal = $("#uName").val();
			var priorityVal = $("input[name='uPriority']:checked").val();
			var goalDateVal = $('#datepicker').datepicker({ dateFormat: 'yy-mm-dd' }).val();
			
			$.ajax({
				type : "post",
				url : "/todo/modify/"+id,
				headers : {
					"Content-type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				contentType: 'application/json; charset=utf-8',
				dataType : "text",
				data : JSON.stringify({
					"id" : id,
					"title" : titleVal,
					"name" : nameVal,
					"priority" : priorityVal,
					"goalDate" : goalDateVal
				}),
				success : function(result) {
					if (result == "modify success") {
						alert("할 일이 수정되었습니다");
						window.location = "/todo/main?page=1&range=1";
					}
				},
				error: function(request, status, error) {
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
		

		// 이전 버튼 이벤트
		function prevEvent(page, range, rangeSize) {
			var page = ((range - 1) * rangeSize);
			var range = range - 1;

			var url = "${cp}/main";
			url += "?page=" + page + "&range=" + range;

			location.href = url;
		}

		// 페이지 번호 버튼 이벤트
		function pageEvent(page, range, rangeSize) {
			var url = "${cp}/main";

			url += "?page=" + page + "&range=" + range;

			location.href = url;
		}

		// 다음 버튼 이벤트
		function nextEvent(range, rangeSize) {
			var page = parseInt(range * rangeSize) + 1;
			var range = parseInt(range) + 1;

			var url = "${cp}/main";
			url += "?page=" + page + "&range=" + range;

			location.href = url;
		}

	</script>
</body>
</html>