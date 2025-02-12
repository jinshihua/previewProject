package preview.service.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import preview.mapper.RoleMapper;
import preview.po.RolePO;
import preview.service.impl.RoleService;
import preview.vo.RoleVO;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Override
    public RoleVO getRoleById(Long id) throws InvocationTargetException, IllegalAccessException {
        RolePO rolePO = baseMapper.selectById(id);
        if(rolePO==null){
            return null;
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(rolePO,roleVO);
        return roleVO;
    }

    @Override
    public boolean deleteRole(Long id) {
       return baseMapper.deleteById(id)>0;
    }

    @Override
    public boolean updateRole(Long id,RolePO rolePO) {
        rolePO.setId(id);
        rolePO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.updateById(rolePO)>0;
    }

    @Override
    public boolean createRole(RolePO rolePO) {
        rolePO.setCreatedTime(LocalDateTime.now());
        rolePO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.insert(rolePO)>0;
    }

    @Override
    public List<RoleVO> RoleList() {
        List<RolePO> rolePOS = baseMapper.selectList(null);
        List<RoleVO> roleVOList = rolePOS.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            try {
                BeanUtils.copyProperties(role, roleVO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return roleVO;
        }).collect(Collectors.toList());
        return roleVOList;
    }
}
