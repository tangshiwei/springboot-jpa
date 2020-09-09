package com.jpa.node;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树菜单节点
 */
@Data
public class TreeNode implements Serializable {

    private Long id;
    //节点标题
    private String title;
    private String icon;
    //节点字段名
    private String field;
    //节点是否初始为选中状态（如果开启复选框的话），默认 false
    private boolean checked;
    //节点是否初始展开，默认 false
    private boolean spread;
    //节点是否为禁用状态。默认 false
    private boolean disabled;
    //点击节点弹出新窗口对应的 url。需开启 isJump 参数
    private String href;
    //子节点。支持设定选项同父节点
    private List<TreeNode> children;

    //数据库连接Id
    private Long connectId;
    private String databaseName;


    //1.连接,2.数据库，3，表，4.视图，5.函数，6.存储过程,7.事件，8.查询

    //树节点对应的页面Url
//    public static final String HREF_DB = "/navicat/database-list.html";
//    public static final String HREF_TABLE = "/navicat/table-list.html";
//    public static final String HREF_VIEW = "/navicat/view-list.html";
//    public static final String HREF_FUNCTION = "/navicat/function-list.html";
//    public static final String HREF_PROCEDURE = "/navicat/procedure-list.html";
//    public static final String HREF_TRIGGER = "/navicat/trigger-list.html";
//    public static final String HREF_SQL = "/navicat/dbsql-list.html";

}
