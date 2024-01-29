package com.yly.uniform.all.vo;

import javax.validation.groups.Default;

/**
 * 分组校验接口
 * @author jam
 * @date 2021/7/30 1:54 下午
 */
public interface ValidGroup extends Default {
    interface Crud extends ValidGroup{
        interface Create extends Crud{

        }

        interface Update extends Crud{

        }

        interface Query extends Crud{

        }

        interface Delete extends Crud{

        }
    }

}
