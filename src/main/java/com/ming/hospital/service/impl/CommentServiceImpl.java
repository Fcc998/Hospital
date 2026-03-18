package com.ming.hospital.service.impl;

import com.ming.hospital.dao.CommentMapper;
import com.ming.hospital.dao.DoctorMapper;
import com.ming.hospital.pojo.Comment;
import com.ming.hospital.pojo.Doctor;
import com.ming.hospital.service.CommentService;
import com.ming.hospital.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    @Transactional
    public boolean addComment(Comment comment) {
        comment.setCid(CommonUtils.getId());
        comment.setCreatetime(new Date());
        comment.setUpdatetime(new Date());
        
        int result = commentMapper.insert(comment);
        
        if (result > 0) {
            Double avgScore = commentMapper.getAverageScoreByDoctorId(comment.getDid());
            if (avgScore != null) {
                Doctor doctor = new Doctor();
                doctor.setDid(comment.getDid());
                doctor.setScore(avgScore.floatValue());
                doctorMapper.updateByPrimaryKeySelective(doctor);
            }
        }
        
        return result > 0;
    }

    @Override
    public List<Comment> getCommentsByDoctorId(Long did) {
        return commentMapper.selectByDoctorId(did);
    }

    @Override
    public Double getAverageScore(Long did) {
        return commentMapper.getAverageScoreByDoctorId(did);
    }

    @Override
    public int getCommentCount(Long did) {
        return commentMapper.countByDoctorId(did);
    }
}
