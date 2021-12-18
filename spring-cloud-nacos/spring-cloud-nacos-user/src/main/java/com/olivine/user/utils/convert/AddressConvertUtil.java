package com.olivine.user.utils.convert;

import com.olivine.user.domain.AddressDO;
import com.olivine.user.dto.AddressDTO;
import org.springframework.beans.BeanUtils;

/**
 * @Author jphao
 * @Date 2021/11/26 22:40
 * @Description
 */
public class AddressConvertUtil {

    public static AddressDO dtoToDO(AddressDTO addressDTO) {
        if (addressDTO == null)
            return null;
        AddressDO addressDO = new AddressDO();
        BeanUtils.copyProperties(addressDTO, addressDO);
        return addressDO;
    }

    public static AddressDTO doToDTO(AddressDO addressDO) {
        if (addressDO == null)
            return null;
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(addressDO, addressDTO);
        return addressDTO;
    }
}
