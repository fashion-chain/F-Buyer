package com.hottop.core.utils.tree;

import com.hottop.core.model.commerce.CommerceCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建tree 嵌套Node 数据结构
 */
public class TreeUtil<T extends TreeNodeInterface> {


    public void getTree(Node result, Long pid, List<T> list) {
        List<T> tmpList = findCategoriesByPId(list, pid);
        if (tmpList.size() == 0) {
            return;
        }else {
            ArrayList<Node> children = result.getChildren();
            for (T c : tmpList) {
                Node node = new Node();
                node.setLabel(c.getLabel());
                node.setValue(c.getValue());
                children.add(node);
            }
            result.setChildren(children);
            for(Node node : children) {
                getTree(node, node.getValue(), list);
            }
        }
    }


    //根据pid 加载它下面的list
    private List<T> findCategoriesByPId(List<T> list, Long pid){
        ArrayList<T> result = new ArrayList<>();
        for(T c : list) {
            if(c.getParentId() == pid){
                result.add(c);
            }
        }
        return result;
    }
}
