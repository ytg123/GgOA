package com.gg.ssm.framework.utils;

import com.github.pagehelper.PageInfo;


/**
 * 生成界面上的分页组件
 */
public class PageUtils {

   public static String pageStr (PageInfo pageInfo,String queryMethod) {

        StringBuilder sb = new StringBuilder("<ul>");
        //判断当前页是不是首页
        if (pageInfo.isIsFirstPage()
                || pageInfo.getPrePage() == 0) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>");
        } else {
            sb.append("<li><a href=\"javascript:"+queryMethod+"(");
            sb.append(pageInfo.getPrePage()).append(",");
            sb.append(pageInfo.getPageSize()).append(")\">&#171; 上一页</a></li>");
        }

        for (int i = 0; i < pageInfo.getNavigatepageNums().length; i++) {
            int pageNum = pageInfo.getNavigatepageNums()[i];
            if (pageInfo.getPageNum() == pageNum) {
                sb.append("<li class=\"active\"><a href=\"javascript:\">");
                sb.append(pageNum).append("</a></li>");
            } else {
                sb.append("<li><a href=\"javascript:"+queryMethod+"(");
                sb.append(pageNum).append(", ");
                sb.append(pageInfo.getPageSize()).append(")\">");
                sb.append(pageNum).append("</a></li>");
            }
        }

        //判断是否是尾页
        if (pageInfo.isIsLastPage() || pageInfo.getNextPage() == 0) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>");
        } else {
            sb.append("<li><a href=\"javascript:"+queryMethod+"(");
            sb.append(pageInfo.getNextPage()).append(",");
            sb.append(pageInfo.getPageSize()).append(")\">下一页 &#187;</a></li>");
        }

        sb.append("<li class=\"disabled controls\"><a href=\"javascript:void(0);\">当前第 ");
        sb.append("<input type=\"text\" maxLength=\"6\" value=\"");
        sb.append(pageInfo.getPageNum());
        sb.append("\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)"+queryMethod+"(this.value,");
        sb.append(pageInfo.getPageSize()).append(");\" onclick=\"this.select();\"/>");
        sb.append(" 页 / 共 ");
        sb.append(pageInfo.getPages());
        sb.append(" 页， 共 ");
        sb.append(pageInfo.getTotal());
        sb.append(" 条</a></li></ul>");

        return sb.toString();

    }


}
