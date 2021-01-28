/**
 *  read.jsp 자바스크립트
 */
$(function() {
	//댓글 리스트 함수 호출
	showList(1);
	var form = $("#myform");

	$(".btn-default").click(function() {
		form.submit();
	})

	$(".btn-info").click(function() {
		form.find("input[name='bno']").remove();
		form.attr('action', 'list');
		form.submit();
	})
	
	//댓글 작업 
	//댓글 삽입 - bno, reply(댓글 내용), replyer(댓글 작성자)
	$("#addReplyBtn").click(function(){
		
		//기본 디자인에서 댓글 입력을 위한 화면으로 변경
		
		
		modal.find("input").val("");
		
		//readonly 속성 없애기
		modalInputReplyer.prop("readonly", "");
		
		//작성일자 요소를 요소 숨기기
		modalInputReplyDate.closest("div").hide();	//작성 요소를 감싸고 있는 div를 찾아 hide()해준다.
		//등록, 닫기 버튼만 보여주기
		//①닫기 버튼을 제외한 모든 버튼 숨기기
		modal.find("button[id!='modalCloseBtn']").hide();
		//②등록 버튼 보여주기
		modalRegisterBtn.show();
		
		modal.modal("show");
		
			
	})
	
	modalRegisterBtn.click(function(){
		
		var reply = {bno:bnoVal,reply:modalInputReply.val(),replyer:modalInputReplyer.val()};
		
		replyService.add(reply, function(result){
			if(result){
				//alert("result : "+result);
				
				//성공 메세지가 돌아오면
				//1) 현재 모달창에 있는 내용 모두 지우기
				modal.find("input").val("");
				//2)모달창 닫기
				modal.modal("hide");
				//3)리스트 보여주기
				showList(-1);
			}
		});//add end
	})
	
	function showList(page) {
		//댓글 리스트 가져오기 -1577/1
		replyService.getList({ bno: bnoVal, page: page }, function(total,data) {
			//console.log(data);

			//새글 작성시 http://localhost:8080/replies/pages/-1
			if(page==-1){//마지막 페이지를 알아내기 위해 작업
				pageNum=Math.ceil(total/10.0);
				showList(pageNum);
				return
			}
			
			//보여줄 댓글이 없다면
			if (data == null || data.length == 0) {
				replyUl.html("");
				return;
			}
			
			//댓글이 존재한다면
			let str = "";
			for (var i = 0,len= data.length||0; i < len; i++) {
				str += '<li class="left clearfix" data-rno="'+data[i].rno+'">';
				str += '<div><div class="header">';
				str += '<strong class="primary-front">'+data[i].replyer+'</strong>';
				str += '<small class="pull-right text-muted">'+replyService.displayTime(data[i].replyDate)+'</small>';
				str += '<p>'+data[i].reply+'</p>';
				str += '</div></div></li>';
			}
			replyUl.html(str);
			showReplyPage(total);
		});//getList end
	}
	
	//댓글 페이지 영역 => 1234
	var pageNum = 1;
	function showReplyPage(total){
		console.log("total : "+total);
		
		//마지막 페이지 계산 
		var endPage=(Math.ceil(pageNum/10.0))*10;
		//시작 페이지
		//10-9 = 1
		var startPage = endPage-9;
		//이전 버튼
		var prev=startPage!=1;
		//다음 버튼
		var next=false;
		if(endPage*10>=total){
			endPage=Math.ceil(total/10.0);
		}
		if(endPage*10<total){
			next=true;
		}
		
		var str="<ul class='pagination pull-right'>";
		if(prev){
			str+="<li class='page-item'><a class='page-link' href='"+(startPage-1)+"'>";
			str+="Previous</a></li>";
		}
		for(var i=startPage;i<=endPage;i++){
			var active=pageNum==i?"active":"";
			str+="<li class='page-item "+active+"'>";
			str+="<a class='page-link' href='"+i+"'>"+i+"</a></li>";
		}
		if(next){
			str+="<li class='page-item'><a class='page-link' href='"+(endPage-1)+"'>";
			str+="Next</a></li>";
		}
		str+="</ul>";
		replyPageFooter.html(str);
	}
	//댓글 페이지 나누기 번호 클릭시 동작
	replyPageFooter.on("click", "li a", function(e){
		e.preventDefault();	//마우스 올리면 해당 path로 이동하는 기능을 막음
		pageNum=$(this).attr("href");
		showList(pageNum);
	})
	
	
	//댓글 삭제
	$(modalRemoveBtn).click(function(){
		replyService.remove(modal.data("rno"), function(result){
			if(result){
				//alert("result : "+result);
				modal.modal("hide");
				showList(pageNum);
			}
		}); //remove end	
	})
	

	//댓글 수정
	$(modalModBtn).click(function(){
		var reply={
					rno:modal.data("rno"),
					reply:modalInputReply.val()
		};
		replyService.update(reply, function(result){
			if(result){
				//alert("result : "+result);
				modal.modal("hide");
				showList(pageNum);
			}
		});	//update end
	})

	//댓글 하나 가져오기
	//이벤트 위임 : li 태그는 나중에 생기는 요소이기 때문에 현재 있는 요소에 이벤트를 걸고
	//	         나중에 li태그에 넘겨주는 방식
	$(replyUl).on("click", "li", function(){
		
		//현재 클릭된 li 요소의 rno 가져오기
		var rno = $(this).data("rno");
		console.log("rno - "+rno);
		
		replyService.get(rno, function(data) {
			if (data != null) {
				console.log(data);
				
				//요청한 댓글 화면에 보여주기
				modalInputReply.val(data.reply);
				modalInputReplyer.val(data.replyer).attr("readonly", "readonly");
				modalInputReplyDate.val(replyService.displayTime(data.replyDate)).attr("readonly", "readonly");
				modal.data("rno", data.rno);
				
				//숨겨졌던 작성 날짜 영역 보여주기
				modalInputReplyDate.closest("div").show();
				modal.find("button").show();
				modal.find("button[id='modalRegisterBtn']").hide();
				modal.modal("show");
			}
		});
	})
	
})

//첨부물
//$(function) : jquery 시작, $(document).read(function(){})을 줄여쓰는 방식
$(document).ready(function(){
	
	var uploadResult=$(".uploadResult ul");
	
	$.getJSON({
		url:'getAttachList',
		data:{
			bno:bnoVal	
		},
		success:function(data){
			console.log(data);
			
			var str="";
			
			$(data).each(function(idx, obj){
				if(obj.fileType){
					//썸네일 이미지 경로
					var fileCallPath = encodeURIComponent(obj.uploadPath+"\\s_"+obj.uuid+"_"+obj.fileName);
					
					str+="<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' ";
					str+="data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>";
					str+="<img src='/display?fileName="+fileCallPath+"'><div>"+obj.fileName;
					str+="</div></li>";
				}else{
					
					str+="<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' ";
					str+="data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>";
					str+="<img src='/resources/img/attach.png'><div>"+obj.fileName+"</a>";
					str+="</div></li>";
				}
			})
			uploadResult.append(str);
		}		
	})//getJSON 종료
	
	//이미지 클릭 시 원본 이미지 보여주기
	//일반 파일 다운로드
	$(uploadResult).on("click", "li", function(){
		var liObj = $(this);
		
		var path = encodeURIComponent(liObj.data("path")+"\\"+liObj.data("uuid")+"_"+liObj.data("filename"));
		
		if(liObj.data("type")){
			showImage(path.replace(new RegExp(/\\/g), "/"));
		}else{
			location.href="/download?fileName="+path;
		}
	})
	
	//크게 열린 이미지 다시 닫기
	$(".bigPictureWrapper").click(function(){
		$(".bigPicture").animate({width:'0%', height:'0%'}, 1000);
		setTimeout(function(){
			$(".bigPictureWrapper").hide();
		}, 1000);
	})//이미지 닫기 종료
})
function showImage(fileCallPath){
	$(".bigPictureWrapper").css("display", "flex").show();
	$(".bigPicture").html("<img src='/display?fileName="+fileCallPath+"'>")
					.animate({width:'100%', height:'100%'}, 1000);
}