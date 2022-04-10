package com.deep.product.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.params.AttrGroupRelationParam;
import com.deep.product.model.vo.AttrVO;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author Deep
 * @date 2022/3/18
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {
    /**
     * 通过属性id获取属性分组名
     *
     * @param attrId 属性id
     * @return 分组名称
     */
    String getGroupNameByAttrId(@NonNull Long attrId);

    /**
     * 获取关联属性
     */
    List<AttrVO> getAttrsByGroupId(@NonNull Long groupId);

    /**
     * 获取没有进行关联的属性页
     */
    PageUtils getNoRelationAttrPage(Map<String, Object> params, @NotNull Long groupId);

    /**
     * 新增属性关联
     */
    void saveRelation(List<AttrGroupRelationParam> params);

    /**
     * 移除关联
     */
    void deleteRelations(List<AttrGroupRelationParam> params);

    /**
     * 按照属性id集合删除关联关系
     */
    void removeByAttrIds(@NonNull List<Long> attrIds);
}
