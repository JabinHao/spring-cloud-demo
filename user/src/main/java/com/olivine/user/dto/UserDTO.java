package com.olivine.user.dto;

import com.olivine.user.remote.order.dto.OrderDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 22:07
 * @Description
 */
@Data
public class UserDTO  implements Serializable{
    private static final long serialVersionUID = 2763416354953361644L;

    private String uid;

    private String gender;

    // 用户昵称
    private String name;

    // 收货地址
    private List<AddressDTO> address;

    private List<OrderDTO> orders;

    private Date ctime;

    private Date mtime;
}
