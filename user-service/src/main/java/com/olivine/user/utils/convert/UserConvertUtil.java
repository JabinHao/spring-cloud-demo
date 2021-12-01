package com.olivine.user.utils.convert;

import com.olivine.user.domain.AddressDO;
import com.olivine.user.domain.UserDO;
import com.olivine.user.dto.AddressDTO;
import com.olivine.user.dto.UserDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author jphao
 * @Date 2021/11/26 22:34
 * @Description
 */
public class UserConvertUtil {

    public static UserDO dtoToUserDO(UserDTO userDTO){
        if (userDTO == null)
            return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        return userDO;
    }

    public static List<AddressDO> dtoToAddressDO(UserDTO userDTO){
        if (userDTO == null)
            return null;
        final List<AddressDTO> addressDTOList = userDTO.getAddress();

        if (addressDTOList == null || addressDTOList.size() == 0)
            return null;
        final List<AddressDO> addressDOList = addressDTOList.parallelStream().map(AddressConvertUtil::dtoToDO).collect(Collectors.toList());
        return addressDOList;
    }

    public static UserDTO doToDTO(UserDO userDO, List<AddressDO> addressDOList){
        if (userDO == null)
            return null;
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        final List<AddressDTO> addressDTOList;
        addressDTOList = addressDOList.parallelStream().map(AddressConvertUtil::doToDTO).collect(Collectors.toList());

        userDTO.setAddress(addressDTOList);
        return userDTO;
    }
}
