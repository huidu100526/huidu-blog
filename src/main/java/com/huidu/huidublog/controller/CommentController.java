package com.huidu.huidublog.controller;

import com.huidu.huidublog.entity.Blog;
import com.huidu.huidublog.entity.Comment;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.service.BlogService;
import com.huidu.huidublog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/24 15:42
 * @Description: 评论博客相关controller
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 打开博客详情后获取评论列表进行返回
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        // 根据博客id获取评论列表
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    /**
     * 提交评论进行保存
     */
    @PostMapping("/comments")
    public String post(Comment comment, Long parentCommentId, Long blogId, HttpSession session) {
        // 根据此博客id查询博客进行保存至评论信息中
        Blog blog = blogService.getBlog(blogId);
        comment.setBlog(blog);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            // 设置评论人默认头像
            comment.setAvatar(avatar);
        }
        // 根据评论内容和父标签id进行保存评论信息
        commentService.saveComment(comment, parentCommentId);
        return "redirect:/comments/" + blogId;
    }
}
