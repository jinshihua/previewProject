package preview.service.impl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import preview.mapper.RolePermissionMapper;
import preview.po.RolePermissionPO;
import preview.service.impl.RolePermissionService;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionPO> implements RolePermissionService {
    @Override
    public boolean createRolePermission(RolePermissionPO rolePermissionPO) {
        return baseMapper.insert(rolePermissionPO) > 0;
    }

    @Override
    public boolean deleteRolePermission(Long roleId, Long permissionId) {
        QueryWrapper<RolePermissionPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId).eq("permission_id",permissionId);
        return baseMapper.delete(queryWrapper)>0;
    }
}
