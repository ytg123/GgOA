/**
 * 判断非空
 * 
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	val = $.trim(val);
	if (val == null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}


function isNotEmpty(val) {
	return !isEmpty(val);
}

//判断两个元素是否相等
function eqauls(str,tstr){
	if(str == tstr){
		return true;
	}
	return false;
}


/**   
 *转换long值为日期字符串   
 * @param longtime 时间   
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
 * @return 符合要求的日期字符串   
 */    

 function getFormatDateByLong(longTime, pattern) {  
     return getFormatDate(new Date(longTime), pattern);  
 }  
 /**   
 *转换日期对象为日期字符串   
 * @param l long值   
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
 * @return 符合要求的日期字符串   
 */    
 function getFormatDate(date, pattern) {  
     if (date == undefined) {  
         date = new Date();  
     }  
     if (pattern == undefined) {  
         pattern = "yyyy-MM-dd hh:mm:ss";  
     }  
    
     var o = {  
             "M+": date.getMonth() + 1,  
             "d+": date.getDate(),  
             "h+": date.getHours(),  
             "m+": date.getMinutes(),  
             "s+": date.getSeconds(),  
             "q+": Math.floor((date.getMonth() + 3) / 3),  
             "S":  date.getMilliseconds()  
         };  
     if (/(y+)/.test(pattern)) {  
    	 pattern = pattern.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));  
     }  
     
     for (var k in o) {  
         if (new RegExp("(" + k + ")").test(pattern)) {  
        	 pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
         }  
     }  
     
     return pattern;  
 };  
 
