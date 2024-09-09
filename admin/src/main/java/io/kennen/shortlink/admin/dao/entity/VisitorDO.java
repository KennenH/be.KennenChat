package io.kennen.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("visitors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDO {

    private Long id;
    private String ip;
    private Integer gitVisits;
    private Integer sendMessages;
}
