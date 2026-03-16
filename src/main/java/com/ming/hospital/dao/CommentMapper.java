package com.ming.hospital.dao;

import com.ming.hospital.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByDoctorId(Long did);

    Float getAvgScoreByDoctorId(Long did);

    int countByDoctorId(Long did);

    List<Comment> selectByDoctorIdWithUser(Long did);
}
