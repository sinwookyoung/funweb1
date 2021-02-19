<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- head 컨텐트 영역 --%>
<jsp:include page="/WEB-INF/views/include/headContent.jsp" />

<link href="/css/subpage.css" rel="stylesheet" type="text/css"  media="all">
</head>

<body>
<div id="wrap">
	<%-- header 영역 --%>
	<jsp:include page="/WEB-INF/views/include/topHeader.jsp" />
  
	<div class="clear"></div>
	<div id="sub_img"></div>
  
	<div class="clear"></div>
	<%-- nav 영역 --%>
	<jsp:include page="/WEB-INF/views/include/submenuCompany.jsp" />
	
	<article>
		<h1>Charts</h1>
		<div id="chart1" style="width: 100%; height: 500px;"></div>
		<div id="chart2" style="width: 100%; height: 500px;"></div>
	</article>
    
	<div class="clear"></div>
	<%-- footer 영역 --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />
</div>


<script src="/script/jquery-3.5.1.js"></script>
<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>
// 구글 시각화 API를 로딩하는 메소드
google.charts.load('current', {packages: ['corechart']});

// 구글 시각화 API가 로딩이 완료되면,
// 인자로 전달된 콜백함수를 내부적으로 호출하여 차트를 그리는 메소드
google.charts.setOnLoadCallback(function () {

	getChartDataMemberAndDraw();
});

function getChartDataMemberAndDraw() {
	// 남녀 성별 회원수, 연령대별 회원수 ajax 요청하기
	$.ajax({
		url: '/ajax/chartDataMember',
		method: 'GET',
		contentType: 'application/json; charset=UTF-8',
		success: function (response) {
			console.log(typeof response);
			console.log(response);

			drawChart1(response.genderPerCountList);
			drawChart2(response.agePerCountList);
		}
	});

	//setTimeout(getChartDataMemberAndDraw, 5000); // 5초 간격으로 반복 호출. 재귀함수.
}


// 파이 그래프 그리기
function drawChart1(arr) {
	var dataTable = google.visualization.arrayToDataTable(arr);
	
	var options = {
			title: '남녀 성별 회원수'
	};
	
	var objDiv = document.getElementById('chart1');
	var chart = new google.visualization.PieChart(objDiv);
	chart.draw(dataTable, options);
}

// 막대 그래프 그리기
function drawChart2(arr) {
	// 실 데이터를 가진 데이터테이블 객체를 반환하는 메소드
	var dataTable = google.visualization.arrayToDataTable(arr);
	// 옵션객체 준비
	var options = {
			title: '나이대 별 회원수',
			hAxis: {
				title: '나이대',
				titleTextStyle: {
					color: 'red'
				}
			}
	};
	// 차트를 그릴 영역인 div 객체를 가져옴 
	var objDiv = document.getElementById('chart2');
	// 인자로 전달한 div 객체의 영역에 컬럼차트를 그릴수 있는 차트객체를 반환
	var chart = new google.visualization.ColumnChart(objDiv);
	// 차트객체에 데이터테이블과 옵션 객체를 인자로 전달하여 차트 그리는 메소드
	chart.draw(dataTable, options);
}
</script>
</body>
</html>   

    