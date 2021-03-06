/**
 * 	댓글 스크립트
 */

//자바스크립트 클로저
var replyService = (function(){
	function add(reply, callback){
		console.log("add method 호출");
		
		$.ajax({
			type:'post',
			url:'/replies/new',
			contentType:'application/json;charset=utf-8',
			data:JSON.stringify(reply),
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		})
	}//add end
	
	function getList(param, callback){
		console.log("getList 호출");
		
		var bno = param.bno;
		var page = param.page||1;
		
		$.getJSON({
			url:"/replies/pages/"+bno+'/'+page,
			success:function(data){
				if(data!=null){
					if(callback){
						//console.log(data.replyCnt, data.list);
						callback(data.replyCnt, data.list);
					}
				}
			}
		})
	}//getList end
	
	function remove(rno, replyer, callback){
		console.log("remove 호출");
		$.ajax({
			url:'/replies/'+rno,
			type:'delete',
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				replyer:replyer
			}),
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		})
	}//remove end
	
	function update(reply, callback){
		console.log("update 호출");
		$.ajax({
			url:'/replies/'+reply.rno,
			type:'put',
			contentType:'application/json;charset=utf-8',
			data:JSON.stringify(reply),
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		})
	}//update end
	
	function get(rno, callback){
		console.log("댓글 하나 요청");
		
		$.getJSON({
			url:'/replies/'+rno,
			success:function(data){
				if(callback){
					callback(data);
				}
			}
		})
	}//get end
	
	//날짜 포멧 변경 함수
	function displayTime(timeVal){
		console.log(timeVal);
		
		var today=new Date();
		
		var gap = today.getTime()-timeVal;
		var dateObj = new Date(timeVal);
		
		if(gap<(1000*60*60*24)){//댓글 단 날짜가 오늘이라면
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [(hh>9?'':'0')+hh,':',(mi>9?'':'0')+mi,':',(ss>9?'':'0')+ss].join('');
		}else{
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth()+1;
			var dd = dateObj.getDate();
			//return [yy,'/',(mm>9?'':'0')+mm,'/',(dd>9?'':'0')+dd].join('');
			return [yy,(mm>9?'':'0')+mm,(dd>9?'':'0')+dd].join('/');
		}
	}
	
	return{
		add:add,
		getList:getList,
		remove:remove,
		update:update,
		get:get,
		displayTime:displayTime
	}
})();