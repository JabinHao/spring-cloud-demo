package com.olivine.user.mapper;

import com.olivine.user.domain.AddressDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 19:53
 * @Description
 */
public interface AddressMapper {

    int insert(AddressDO addressDO);

    int insertBatch(@Param("entities") List<AddressDO> entities);

    List<AddressDO> selectByUid(String uid);

    int updateByUid(AddressDO addressDO);

    int deleteByUid(String uid);

    void updateBatchByUid(List<AddressDO> addressDOList);
}
