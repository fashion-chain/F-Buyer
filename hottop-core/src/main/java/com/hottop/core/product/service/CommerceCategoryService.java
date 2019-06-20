package com.hottop.core.product.service;

import com.hottop.core.product.support.CategoryNode;
import com.hottop.core.model.commerce.CommerceCategory;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceCategoryRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.tree.Node;
import com.hottop.core.utils.tree.TreeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 品类 service
 */
@Service("commerceCategoryService")
public class CommerceCategoryService extends EntityBaseService<CommerceCategory,Long>{

    @Autowired
    private CommerceCategoryRepository commerceCategoryRepository;

    @Override
    public EntityBaseRepository<CommerceCategory, Long> repository() {
        return commerceCategoryRepository;
    }

    private void transferIfFieldsNotBlank(CommerceCategory source, CommerceCategory destination) {
        if(StringUtils.isNotBlank(source.getName())){
            destination.setName(source.getName());
        }
        if(source.getParentId() != null ){
            destination.setParentId(source.getParentId());
        }
        if(source.getSpecifications() != null){
            destination.setSpecifications(source.getSpecifications());
        }
        if(source.getAttributes() != null){
            destination.setAttributes(source.getAttributes());
        }
    }

    @Transactional
    public Response updateCategory(CommerceCategory commerceCategory, Long id) {
        try {
            CommerceCategory category_repo = findOne(CommerceCategory.class, id);
            transferIfFieldsNotBlank(commerceCategory, category_repo);
            if(category_repo.getDeleteTime() != null) category_repo.setDeleteTime(null);
            CommerceCategory update = update(category_repo);
            return ResponseUtil.updateSuccessResponse(CommerceCategory.class.getSimpleName());
        }catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
        return ResponseUtil.updateFailResponse(CommerceCategory.class.getSimpleName());
    }
    

    public Response getCategoryTree() {
        List<CommerceCategory> list = commerceCategoryRepository.findAllByDeleteTimeIsNull();
        Node result = new Node();
        TreeUtil<CommerceCategory> treeUtil = new TreeUtil<>();
        treeUtil.getTree(result, 0l, list);
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result.getChildren()).create();
    }

    //根据分类id查询分类树，id对应的分类肯定展示（不论删不删除）
    public Response getCategoryTree(Long id) {
        List<CommerceCategory> all = commerceCategoryRepository.findAll();
        Map<Long, CommerceCategory> categoryMap_result = all.stream().filter(c -> c.getDeleteTime() == null).collect(Collectors.toMap(k -> k.getId(), v -> v, (k1, k2) -> k1));
        Map<Long, CommerceCategory> categoryMap_all = all.stream().collect(Collectors.toMap(k -> k.getId(), v -> v, (k1, k2) -> k1));
        Long category_id = id;
        if(categoryMap_all.containsKey(category_id) ) {
            categoryMap_result.put(category_id, categoryMap_all.get(category_id));
            CommerceCategory parent = categoryMap_all.get(categoryMap_all.get(category_id).getParentId());
            while (parent != null) {
                categoryMap_result.put(parent.getId(), parent);
                parent = categoryMap_all.get(parent.getParentId());
            }
        }
        Node result = new Node();
        TreeUtil<CommerceCategory> treeUtil = new TreeUtil<>();
        treeUtil.getTree(result, 0l, new ArrayList<>(categoryMap_result.values()));
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result.getChildren()).create();
    }

    private static Map<Long,String> categoryIdNameMap;
    public Map<Long,String> getCategoryIdNameMap() {
        if(categoryIdNameMap == null) {
            categoryIdNameMap = new LinkedHashMap<>();
        }
        List<CommerceCategory> list = commerceCategoryRepository.findAllByDeleteTimeIsNull();
        Map<Long, CommerceCategory> map = list.stream().collect(Collectors.toMap(k -> k.getId(), v->v, (k1, k2) -> k1));
        Iterator<Long> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long next = iterator.next();
            String categoryShowName = map.get(next).getName();
            CommerceCategory commerceCategory = map.get(next);
            CommerceCategory parent = map.get(commerceCategory.getParentId());
            while (parent != null ) {
                categoryShowName = parent.getName() + "/" + categoryShowName;
                parent = map.get(parent.getParentId());
            }
            categoryIdNameMap.put(next, categoryShowName);
        }
        return categoryIdNameMap;
    }

    /**

     * @return
     */
    public Response getCategoryListTree() {
        List<CommerceCategory> list = commerceCategoryRepository.findAllByDeleteTimeIsNull();
        CategoryNode node = new CategoryNode();
        getCategoryNode(node, 0l, list);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .simpleMessage("查询品类树List成功").data(node.getChildren()).create();
    }

    /**
     * @param result
     * @param pid
     * @param list
     * @return
     */
    private void getCategoryNode(CategoryNode result, Long pid, List<CommerceCategory> list) {
        List<CommerceCategory> tmpList = findCategoriesByPId(list, pid);
        if(tmpList.size() == 0) {
            return;
        }else {
            ArrayList<CategoryNode> children = result.getChildren();
            for(CommerceCategory c : tmpList) {
                CategoryNode categoryNode = new CategoryNode();
                categoryNode.setKey(c.getId());
                categoryNode.setName(c.getName());
                categoryNode.setAttributes(c.getAttributes());
                categoryNode.setSpecifications(c.getSpecifications());
                children.add(categoryNode);
            }
            result.setChildren(children);
            for(CategoryNode node : children) {
                getCategoryNode(node, node.getKey(), list);
            }
        }

    }

    //根据pid 加载它下面的list
    private List<CommerceCategory> findCategoriesByPId(List<CommerceCategory> list, Long pid){
        ArrayList<CommerceCategory> result = new ArrayList<>();
        for(CommerceCategory c : list) {
            if(c.getParentId() == pid){
                result.add(c);
            }
        }
        return result;
    }

    //判断有没有这个分类id
    public boolean hasNameAlready(String name) {
        int i = commerceCategoryRepository.countByName(name);
        if (i >= 1) {
            return true;
        }
        return false;
    }


}


