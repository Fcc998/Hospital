package com.ming.hospital.web;

import com.ming.hospital.pojo.Comment;
import com.ming.hospital.pojo.User;
import com.ming.hospital.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> addComment(Comment comment, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("msg", "请先登录");
            return result;
        }
        
        comment.setUid(user.getUid());
        boolean success = commentService.addComment(comment);
        result.put("success", success);
        result.put("msg", success ? "评价成功" : "评价失败");
        return result;
    }

    @RequestMapping("/list/{did}")
    @ResponseBody
    public Map<String, Object> getComments(@PathVariable Long did) {
        Map<String, Object> result = new HashMap<>();
        
        List<Comment> comments = commentService.getCommentsByDoctorId(did);
        Double avgScore = commentService.getAverageScore(did);
        int count = commentService.getCommentCount(did);
        
        result.put("success", true);
        result.put("comments", comments);
        result.put("avgScore", avgScore);
        result.put("count", count);
        return result;
    }
}
