package io.kennen.shortlink.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kennen.shortlink.admin.dao.entity.VisitorDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VisitorMapper extends BaseMapper<VisitorDO> {

    @Insert("INSERT INTO kchat.visitors (ip, git_visits, send_messages) VALUES (#{ip}, #{gitVisits}, #{sendMessages}) " +
            "ON DUPLICATE KEY UPDATE send_messages = send_messages + 1")
    void insertOrUpdateOnMessage(VisitorDO visitorDO);

    @Insert("INSERT INTO kchat.visitors (ip, git_visits, send_messages) VALUES (#{ip}, #{gitVisits}, #{sendMessages}) " +
            "ON DUPLICATE KEY UPDATE git_visits = git_visits + 1")
    void insertOrUpdateOnClickGithub(VisitorDO visitorDO);

    @Select("SELECT * FROM kchat.visitors")
    List<VisitorDO> selectAll();
}
