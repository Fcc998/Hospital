package com.ming.hospital.dao;

import com.ming.hospital.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommentMapper {
    int insert(Comment record);
    
    List<Comment> selectByDoctorId(@Param("did") Long did);
    
    Double getAverageScoreByDoctorId(@Param("did") Long did);
    
    int countByDoctorId(@Param("did") Long did);
}
