package preview.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import preview.po.RolePO;
import preview.vo.RoleVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface RoleService extends IService<RolePO> {
    RoleVO getRoleById(Long id) throws InvocationTargetException, IllegalAccessException;
    boolean deleteRole(Long id);
    boolean updateRole(Long id,RolePO rolePO);
    boolean createRole(RolePO rolePO);
    List<RoleVO> RoleList();
}
