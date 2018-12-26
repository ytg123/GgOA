spring + springmvc + mybatis 集成OA系统项目:
	1:登录验证(加密)
	2:密码修改
	3:个人信息修改
	
加密类型介绍:
	不可逆算法:  MD5  SHA1
	可逆算法:BASE64 HEX  
	
分页:
	在后台编写数据库工具方言,屏蔽数据库分页方法的差异
	在后台编写一个分页对象,平布翻页动作带来的查询差异
	
属性表格的应用:
	treeTable
	
在service或者工具类中要获取当前登录用户的信息:
	1:将HttpSession或HttpServletRequest对象作为参数传入service方法
	
	2:spring 提供了一个监听器,用来监听session的变化,然后存储在本地线程
		  <listener>
		  	<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
		  </listener>
		  通过注入
		  	@Autowired
			private HttpSession httpSession;
			//在service中拿到登录用户的信息
			User user = (User) httpSession.getAttribute("user");
			if(user != null){
				menu.setUpdateBy(user.getUserId().toString());
			}
	3:写个工具类
	public class UserInfo {
		public static Long getCurrentUserId(){
			HttpServletRequest request = (HttpServletRequest)RequestContextHolder.getRequestAttributes();
			if(request.getSession().getAttribute("user") != null){
				User user = (User) request.getSession().getAttribute("user");
				return user.getUserId();
			}else{
				return null;
			}
		}
	}