package com.jpa.node;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 一般菜单节点
 */
@Data
public class MenuNode implements Serializable {
    private Long id;
    private String title;
    private String href;
    private String icon;
    private String target="_self";
    private List<MenuNode> child;
    public MenuNode(){}
    public MenuNode(String title, String href, String icon, String target) {
        this.title = title;
        this.href = href;
        this.icon = icon;
        this.target = target;
        this.child = child;
    }
}
