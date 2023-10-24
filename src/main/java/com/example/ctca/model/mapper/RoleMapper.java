package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.RoleDTO;
import com.example.ctca.model.entity.Role;

import java.util.List;

public interface RoleMapper {

    RoleDTO toDTO(Role role);

    List<RoleDTO> toListDTO(List<Role> roleList);

    Role toEntity(RoleDTO roleDTO);
}
