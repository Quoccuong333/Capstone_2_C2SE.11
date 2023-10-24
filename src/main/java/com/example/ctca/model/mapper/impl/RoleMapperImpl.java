package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.RoleDTO;
import com.example.ctca.model.entity.Role;
import com.example.ctca.model.mapper.RoleMapper;
import com.example.ctca.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    RoleService roleService;


    @Override
    public RoleDTO toDTO(Role role) {
        if (role == null){
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        roleDTO.setStatus(role.isStatus());

        return roleDTO;
    }

    @Override
    public List<RoleDTO> toListDTO(List<Role> roleList) {
        if (roleList == null) {
            return null;
        }
        List<RoleDTO> list = new ArrayList<>(roleList.size());
        for (Role role : roleList) {
            RoleDTO roleDTO = toDTO(role);
            if (roleDTO != null) {
                list.add(roleDTO);
            }
        }
        return list;
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null)
            return null;

        Role role = roleService.findById(roleDTO.getId());
        if (role == null)
            role = new Role();

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.isStatus());

        return role;
    }
}
