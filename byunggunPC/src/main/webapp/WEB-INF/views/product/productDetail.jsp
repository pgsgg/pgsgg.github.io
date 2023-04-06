<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<article>
	<h1>상품 상세</h1>
	<div id="itemdetail">
		<form method="post" action="cart_insert" name="formm" id="theform">
			<fieldset>
				<legend> 상품 상세 정보</legend>
				<span style="float: left;"> <img src="images/product/${productVO.image}" style="width: 400px; height: 400px;" />
				</span>
				<h2>${productVO.name}</h2>
				<label> 가 격 : </label>
				<p>
					<fmt:formatNumber type="currency" value="${productVO.price2}" />
				</p>
				<label> 수 량 : </label> <input type="number" min="1" max="20" name="quantity" id="quantity" size="2" value="1" onchange="updateTotal()" style="width: 50px"><br> <input type="hidden" name="pseq" value="${productVO.pseq}"><br>
				<p>
					총 합계: <input type="text" readonly="readonly" id="total" style="border: 0;">
				</p>
				<p>${productVO.content}</p>

			</fieldset>
			<div class="clear"></div>
			<div id="buttons" align="center">
				<input type="button" value="장바구니에 담기" class="submit" onclick="go_cart()"> <input type="button" value="즉시 구매" class="submit" onclick="go_order()"> <input type="reset" value="취소" class="cancel">
			</div>
		</form>
	</div>
	
</article>
<article>
	<div class="container">
		<form id="commentForm" name="commentForm" method="post">
			<br> <br>
			<div>
				<div>
					<span><h3>상품평</h3></span> <span id="cCnt"></span>
				</div>
				<div id="reply">
					<table id="rep_input" style="width: 650px">
						<tr>
							<td style="width: 80%;">
								<textarea rows="3" cols="75" id="content" name="content" placeholder="댓글을 입력하세요"></textarea>
							</td>
							<td style="width: 10%;">
								<a href='#' onClick="save_comment('${productVO.pseq}')" class="btn">등록</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" id="pseq" name="pseq" value="${productVO.pseq}" />
		</form>
	</div>
	<div class="container">
		<form id="commentListForm" name="commentListForm" method="post">
			<div id="commentList"></div>
		</form>
		<!-- 페이지 처리 영역 -->
		<div>
			<ul id="pagination">
			</ul>
		</div>
	</div>
	
</article>
<script type="text/javascript">
	function updateTotal() {
		var quantity = document.getElementById('quantity').value;
		var price = parseInt("${productVO.price2}");
		var total = quantity * price;
		document.getElementById('total').value = total.toLocaleString() + "원";
	}

	window.onload = function() {
		updateTotal();
	}

	$(document).ready(function() {
		// 상품상세정보 로딩 시에 상품평 목록을 조회하여 출력
		getCommentList();
	});

	function getCommentList() {

		$.ajax({
			type : 'get',
			url : 'comment_list',
			datatype : 'json',
			data : $("#commentForm").serialize(),
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			success : function(data) {
				var pageMaker = data.pageInfo;
				var totalCount = data.total;
				var commentList = data.commentList;
				console.log("pageMaker=", pageMaker);
				console.log("count=", totalCount);
				console.log("comments=", commentList);

				showHTML(pageMaker, totalCount, commentList);
			},

			error : function() {
				alert("상품평 목록을 조회하지 못했습니다.")
			}

		})
	}

	function showHTML(pageMaker, totalCount, commentList) {
		var html = "";
		var p_html = "";

		if (commentList.length > 0) {
			$.each(commentList, function(index, item) {
				html += "<div>";
				html += '<div id="comment_item"> <strong>작성자 :' + item.writer
						+ "</strong>";
				html += '<span id="write_date>"' + displayTime(item.regdate) + '</span><br>';
				html += item.content + "<br></div>";
				html += '</div>';
			})
		} else { // 조회된 상품평이 없을 경우
			html += "<div>";
			html += "<h5>등록된 상품평이 없습니다.</h5>";
			html += "</div>";
		}
		$("#commentList").html(html);
	}
	
	function displayTime(timeValue) {
		var today = new Date();
		
		// 현재시간에서 댓글등록시간을 빼줌
		var timeGap = today.getTime() - timeValue;
		
		// timeValue를 Date객체로 변환
		var dateObj = new Date(timeValue);
		
		// timeGap이 24시간 이내인지 판단
		if (timeGap < (1000 * 60 * 60 * 24)) {  // 시, 분, 초를 구함
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			//return hh + ':' + mi + ':' + ss;
			return [(hh>9 ? '':'0')+hh, ':', (mi>9 ? '':'0')+mi, ':', 
				    (ss>9 ? '':'0')+ss].join('');
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			
			//return yy + "-" + mm + "-" + dd;
			return [yy, '/', (mm>9 ? '':'0')+mm, '/', (dd>9 ? '':'0')+dd].join('');
		}
	}
	
	function save_comment(pseq){
		  $.ajax({
		    type : 'post',
		    url : 'comment_save',
		    data : $('#commentForm').serialize(),
		    success : function(data){
		      if (data == 'success'){
		        getCommentList();
		        $('#content').val("");
		      } else if(data == 'fail'){
		        alert("상품평 등록이 실패했습니다.");
		      } else if(data == 'not_logedin'){
		        alert("상품평 등록은 로그인이 필요합니다.");
		      }
		    },
		    error : function(request, status, error){
		      alert("error : " + error);
		    }

		  });
		}
</script>
<%@ include file="../footer.jsp"%>





