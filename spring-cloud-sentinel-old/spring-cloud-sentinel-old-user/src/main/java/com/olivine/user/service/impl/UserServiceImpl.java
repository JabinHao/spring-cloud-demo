package com.olivine.user.service.impl;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.feign.client.order.OrderClient;
import com.olivine.feign.pojo.OrderDTO;
import com.olivine.user.domain.AddressDO;
import com.olivine.user.domain.UserDO;
import com.olivine.user.dto.UserDTO;
import com.olivine.user.mapper.AddressMapper;
import com.olivine.user.mapper.UserMapper;
import com.olivine.user.service.UserService;
import com.olivine.user.utils.convert.UserConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author jphao
 * @Date 2021/11/26 18:27
 * @Description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMEN = "woman";

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final OrderClient orderClient;

    public UserServiceImpl(UserMapper userMapper, AddressMapper addressMapper, OrderClient orderClient) {
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.orderClient = orderClient;
    }

    @Override
    public UserDTO findByUid(String uid) {
        final UserDO userDO = userMapper.selectByUid(uid);
        final List<AddressDO> addressDOList = addressMapper.selectByUid(uid);

        return UserConvertUtil.doToDTO(userDO, addressDOList);
    }

    @Override
    public UserDTO findInfoByUid(String uid) {
        UserDTO userDTO = findByUid(uid);
        if (userDTO == null) {
            return null;
        }

        final CommonResponse<List<OrderDTO>> response = orderClient.getByUid(uid);
        assert response != null;
        final List<OrderDTO> data = response.getData();
        if (data != null){
            userDTO.setOrders(data);
        }
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByUid(UserDTO userDTO) {

        final UserDO userDO = UserConvertUtil.dtoToUserDO(userDTO);
        userMapper.updateByUid(userDO);

        final List<AddressDO> addressDOList = UserConvertUtil.dtoToAddressDO(userDTO);
        if (addressDOList != null) {
            addressMapper.updateBatchByUid(addressDOList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) {
        final String gender = userDTO.getGender();
        if (!Objects.equals(gender, GENDER_MAN) && !Objects.equals(gender, GENDER_WOMEN)) {
            throw new RuntimeException("gender error, must be man or woman");
        }

        final UserDO userDO = UserConvertUtil.dtoToUserDO(userDTO);
        userMapper.insertUser(userDO);

        final List<AddressDO> addressDOList = UserConvertUtil.dtoToAddressDO(userDTO);
        if (addressDOList != null) {
            addressMapper.insertBatch(addressDOList);
        }

        final List<OrderDTO> orderDTOList = userDTO.getOrders();
        if (orderDTOList != null && orderDTOList.size() != 0){
            for (OrderDTO orderDTO : orderDTOList) {
                final CommonResponse<Void> response = orderClient.saveOrder(orderDTO);
                if (response == null || response.getCode() != 200) {
                    log.error("fail to save order:{}, resp:{}", orderDTO, response);
                    throw new RuntimeException("fail to save order");
                }
            }
        }
    }

    @Override
    public void deleteByUid(String uid) {
        userMapper.deleteByUid(uid);
        addressMapper.deleteByUid(uid);
    }

    @Override
    public List<UserDTO> findAll() {
        final List<UserDO> userDOList = userMapper.selectAll();
        final List<UserDTO> userDTOList = userDOList.parallelStream().map(userDO -> {
            final List<AddressDO> addressDOList = addressMapper.selectByUid(userDO.getUid());
            return UserConvertUtil.doToDTO(userDO, addressDOList);
        }).collect(Collectors.toList());

        return userDTOList;
    }


}
