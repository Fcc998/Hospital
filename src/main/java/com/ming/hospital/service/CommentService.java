package com.ming.hospital.service;

import com.ming.hospital.pojo.Comment;
import java.util.List;

public interface CommentService {
    boolean addComment(Comment comment);
    List<Comment> getCommentsByDoctorId(Long did);
    Double getAverageScore(Long did);
    int getCommentCount(Long did);
}
