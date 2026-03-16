package com.ming.hospital.service;

import com.ming.hospital.pojo.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    List<Comment> getCommentsByDoctorId(Long did);

    Float getAvgScoreByDoctorId(Long did);

    int countByDoctorId(Long did);

    List<Comment> getCommentsWithUserByDoctorId(Long did);
}
