package com.huidu.huidublog.VO;

import lombok.Data;

/**
 * @auther huidu
 * @create 2020/2/22 21:47
 * @Description: 博客查询视图表单对象
 */
@Data
public class BlogQuery {
    // 博客标题
    private String title;

    // 博客分类id
    private Long typeId;

    // 是否推荐
    private boolean isRecommend;

    public BlogQuery() {
    }
}
