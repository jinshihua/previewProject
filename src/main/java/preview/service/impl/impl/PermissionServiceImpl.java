package preview.service.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import preview.mapper.PermissionMapper;
import preview.po.PermissionPO;
import preview.service.impl.PermissionService;
import preview.vo.PermissionVO;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionPO>implements PermissionService {
    @Override
    public PermissionVO getPermissionById(Long id) throws InvocationTargetException, IllegalAccessException {
        PermissionPO permissionPO = baseMapper.selectById(id);
        if (permissionPO == null) {
            return null;
        }
        PermissionVO permissionVO = new PermissionVO();
        BeanUtils.copyProperties(permissionPO, permissionVO);
        return permissionVO;
    }

    @Override
    public boolean createPermission(PermissionPO permissionPO) {
        permissionPO.setCreatedTime(LocalDateTime.now());
        permissionPO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.insert(permissionPO) > 0;
    }

    @Override
    public boolean updatePermission(Long id, PermissionPO permissionPO) {
        permissionPO.setId(id);
        permissionPO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.updateById(permissionPO) > 0;
    }

    @Override
    public boolean deletePermission(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public List<PermissionVO> listPermissions() {
        List<PermissionPO> permissionPOS = baseMapper.selectList(null);
        return permissionPOS.stream().map(permissionPO -> {
            PermissionVO permissionVO = new PermissionVO();
            try {
                BeanUtils.copyProperties(permissionPO, permissionVO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return permissionVO;
        }).collect(Collectors.toList());
    }
}
