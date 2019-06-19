package com.hottop.backstage.base.controller;

import com.hottop.core.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获得用户的权限菜单列表
 *
 */
@RestController
@RequestMapping(path = "/interface")
public class InterfaceController {

    @GetMapping(path = "/{username}")
    public Response getUserMenuList() {

        return null;
    }
}
