package com.gengby.starter.extension.crud.model.query;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

import java.io.Serial;

/**
 * 分页查询条件
 *
 * @author Noah
 * @since 1.0.0
 */
@ParameterObject
@Schema(description = "分页查询条件")
public class PageQuery extends SortQuery {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 默认页码：1
     */
    private static final int DEFAULT_PAGE = 1;
    /**
     * 默认每页条数：10
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    @Min(value = 1, message = "页码最小值为 {value}")
    private Integer page = DEFAULT_PAGE;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    @Range(min = 1, max = 1000, message = "每页条数（取值范围 {min}-{max}）")
    private Integer size = DEFAULT_SIZE;

    /**
     * 基于分页查询条件转换为 MyBatis Plus 分页条件
     *
     * @param <T> 列表数据类型
     * @return MyBatis Plus 分页条件
     */
    public <T> IPage<T> toPage() {
        Page<T> mybatisPage = new Page<>(this.getPage(), this.getSize());
        Sort pageSort = this.getSort();
        if (CollUtil.isNotEmpty(pageSort)) {
            for (Sort.Order order : pageSort) {
                OrderItem orderItem = new OrderItem();
                orderItem.setAsc(order.isAscending());
                orderItem.setColumn(CharSequenceUtil.toUnderlineCase(order.getProperty()));
                mybatisPage.addOrder(orderItem);
            }
        }
        return mybatisPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
