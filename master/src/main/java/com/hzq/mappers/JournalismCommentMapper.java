package com.hzq.mappers;

import com.hzq.domain.JournalismComment;

public interface JournalismCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JournalismComment record);

    int insertSelective(JournalismComment record);

    JournalismComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JournalismComment record);

    int updateByPrimaryKey(JournalismComment record);
}