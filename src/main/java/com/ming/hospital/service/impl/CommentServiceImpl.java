package com.ming.hospital.service.impl;

import com.ming.hospital.dao.CommentMapper;
import com.ming.hospital.pojo.Comment;
import com.ming.hospital.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        if (comment.getCid() == null) {
            comment.setCid(System.currentTimeMillis());
        }
        if (comment.getCreatetime() == null) {
            comment.setCreatetime(new Date());
        }
        return commentMapper.insertSelective(comment);
    }

    @Override
    public List<Comment> getCommentsByDoctorId(Long did) {
        return commentMapper.selectByDoctorId(did);
    }

    @Override
    public Float getAvgScoreByDoctorId(Long did) {
        return commentMapper.getAvgScoreByDoctorId(did);
    }

    @Override
    public int countByDoctorId(Long did) {
        return commentMapper.countByDoctorId(did);
    }

    @Override
    public List<Comment> getCommentsWithUserByDoctorId(Long did) {
        return commentMapper.selectByDoctorIdWithUser(did);
    }
}
