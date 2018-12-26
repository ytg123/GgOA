let timeDom = document.getElementById("timer");
function draw(){
		var date = new Date();
		//年月日
		var fullYear = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		if(day < 10){
			day = "0" + day;
		}
		//时分秒
		var h = date.getHours();
		var m = date.getMinutes();
		var s = date.getSeconds();
		//去0
		if(h<10){
			h="0"+h;
		}
		if(m<10){
			m="0"+m;
		}
		if(s<10){
			s="0"+s;
		}
		//星期数
		var week = date.getDay();
		var arr = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
		//获取毫秒数
		var time = date.getTime();
		timeDom.innerHTML = fullYear+"-"+month+"-"+day+"  "+h+":"+m+":"+s+"   "+arr[week];
};
setInterval(function(){
	draw();
},1000);