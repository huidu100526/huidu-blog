package com.huidu.huidublog.controller;

import com.huidu.huidublog.VO.ResultVO;
import com.huidu.huidublog.annotation.PermissionCheck;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.service.BlogService;
import com.huidu.huidublog.service.UserLikeBlogService;
import com.huidu.huidublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @auther huidu
 * @create 2020/2/27 22:42
 * @Description: 博客点赞相关controller
 */
@RestController
public class BlogLikeController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLikeBlogService userLikeBlogService;

    /**
     * 点赞接口
     */
    @GetMapping("/blogLike")
    @ResponseBody
    @PermissionCheck(value = "ROLE_USER")
    public ResultVO blogLike(@AuthenticationPrincipal Principal principal, @RequestParam Long blogId) {
        // 需要用户先登陆才能操作
        if (principal == null) {
            return ResultVO.fial(ResultEnum.USER_NOT_LOGIN.getCode(), ResultEnum.USER_NOT_LOGIN.getMessage());
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        // 如果存在点赞记录返回信息
        if (userLikeBlogService.isLikeByBlogId(blogId, user.getId())) {
            return ResultVO.fial(ResultEnum.BLOG_HAS_LIKE.getCode(), ResultEnum.BLOG_HAS_LIKE.getMessage());
        }
        // 博客添加喜欢数
        Integer likes = blogService.addLikeByBlogId(blogId);
        // 添加喜欢记录
        userLikeBlogService.saveUserLikeBlog(blogId, user.getId());
        return ResultVO.success(ResultEnum.SUCCESS_LIKE_BLOG.getCode(), ResultEnum.SUCCESS_LIKE_BLOG.getMessage(), likes);
    }
}
