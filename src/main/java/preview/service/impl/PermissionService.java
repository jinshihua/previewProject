package preview.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import preview.po.PermissionPO;
import preview.vo.PermissionVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface PermissionService extends IService<PermissionPO> {
    PermissionVO getPermissionById(Long id) throws InvocationTargetException, IllegalAccessException;

    boolean createPermission(PermissionPO permissionPO);

    boolean updatePermission(Long id, PermissionPO permissionPO);

    boolean deletePermission(Long id);

    List<PermissionVO> listPermissions();
}
