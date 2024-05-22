package com.gengby.starter.extension.crud.model.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;
import com.gengby.starter.core.constant.StringConstants;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序查询条件
 *
 * @author Noah
 * @since 1.0.0
 */
@Schema(description = "排序查询条件")
public class SortQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 排序条件
     */
    @Schema(description = "排序条件", example = "createTime,desc")
    private String[] sort;

    /**
     * 解析排序条件为 Spring 分页排序实体
     *
     * @return Spring 分页排序实体
     */
    public Sort getSort() {
        if (ArrayUtil.isEmpty(sort)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>(sort.length);
        if (CharSequenceUtil.contains(sort[0], StringConstants.COMMA)) {
            // e.g "sort=createTime,desc&sort=name,asc"
            for (String s : sort) {
                List<String> sortList = CharSequenceUtil.splitTrim(s, StringConstants.COMMA);
                Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sortList.get(1).toUpperCase()), sortList
                    .get(0));
                orders.add(order);
            }
        } else {
            // e.g "sort=createTime,desc"
            Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sort[1].toUpperCase()), sort[0]);
            orders.add(order);
        }
        return Sort.by(orders);
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }
}
