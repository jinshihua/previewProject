package preview.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import preview.po.RolePermissionPO;

public interface RolePermissionService extends IService<RolePermissionPO> {
    boolean createRolePermission(RolePermissionPO rolePermissionPO);

    boolean deleteRolePermission(Long roleId, Long permissionId);
}
