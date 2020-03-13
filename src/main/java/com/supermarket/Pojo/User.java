package com.supermarket.Pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data

public class User implements Serializable {

    private int usrId;

    private  String usrName;

    private  String usrPassword;

    private  int usrRoleId;

    private  int  usrFlag;
}
