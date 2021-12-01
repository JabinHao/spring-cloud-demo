package com.olivine.user.service.impl;

import com.olivine.user.domain.AddressDO;
import com.olivine.user.domain.UserDO;
import com.olivine.user.dto.UserDTO;
import com.olivine.user.dto.base.CommonResponse;
import com.olivine.user.mapper.AddressMapper;
import com.olivine.user.mapper.UserMapper;
import com.olivine.user.remote.order.dto.OrderDTO;
import com.olivine.user.service.UserService;
import com.olivine.user.utils.convert.UserConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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

    @Resource
    private UserMapper userMapper;
    @Resource
    private AddressMapper addressMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDTO findByUid(String uid) {
        final UserDO userDO = userMapper.selectByUid(uid);
        final List<AddressDO> addressDOList = addressMapper.selectByUid(uid);

        UserDTO userDTO = UserConvertUtil.doToDTO(userDO, addressDOList);
        return userDTO;
    }

    @Override
    @Transactional
    public void updateByUid(UserDTO userDTO) {
        final UserDO userDO = UserConvertUtil.dtoToUserDO(userDTO);
        userMapper.updateByUid(userDO);

        final List<AddressDO> addressDOList = UserConvertUtil.dtoToAddressDO(userDTO);
        if (addressDOList != null)
            addressMapper.updateBatchByUid(addressDOList);
    }

    @Override
    @Transactional
    public int saveUser(UserDTO userDTO) {
        final String gender = userDTO.getGender();
        if (!Objects.equals(gender, "man") && !Objects.equals(gender, "woman"))
            throw new RuntimeException("gender error, must be man or woman");

        final UserDO userDO = UserConvertUtil.dtoToUserDO(userDTO);
        userMapper.insertUser(userDO);

        final List<AddressDO> addressDOList = UserConvertUtil.dtoToAddressDO(userDTO);
        if (addressDOList != null)
            addressMapper.insertBatch(addressDOList);

        final List<OrderDTO> orderDTOList = userDTO.getOrders();
        if (orderDTOList != null && orderDTOList.size() != 0){
            String url = "http://localhost:8081/order/save";
            for (OrderDTO orderDTO : orderDTOList) {
                final CommonResponse response = restTemplate.postForObject(url, orderDTO, CommonResponse.class);
                if (response == null || response.getCode() != 200) {
                    log.error("fail to save order:{}, resp:{}", orderDTO, response);
                    throw new RuntimeException("fail to save order");
                }
            }
        }
        return 1;
    }

    @Override
    public int deleteByUid(String uid) {
        userMapper.deleteByUid(uid);
        addressMapper.deleteByUid(uid);
        return 1;
    }

    @Override
    public List<UserDTO> findAll() {
        final List<UserDO> userDOList = userMapper.selectAll();
        final List<UserDTO> userDTOList = userDOList.parallelStream().map(userDO -> {
            final List<AddressDO> addressDOList = addressMapper.selectByUid(userDO.getUid());
            final UserDTO userDTO = UserConvertUtil.doToDTO(userDO, addressDOList);
            return userDTO;
        }).collect(Collectors.toList());

        return userDTOList;
    }
}
