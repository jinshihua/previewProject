package preview.service.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import preview.mapper.UserMapper;
import preview.po.UserPO;
import preview.service.impl.UserService;
import preview.vo.UserVO;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    @Override
    public UserVO getUserById(Long id) {
        UserPO userPO = baseMapper.selectById(id);
        if (userPO == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        try {
            BeanUtils.copyProperties(userPO, userVO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return userVO;
    }

    @Override
    public boolean createUser(UserPO userPO) {
        userPO.setCreatedTime(LocalDateTime.now());
        userPO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.insert(userPO) > 0;
    }

    @Override
    public boolean deleteUser(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateUser(Long id, UserPO userPO) {
        userPO.setId(id);
        userPO.setUpdatedTime(LocalDateTime.now());
        return baseMapper.updateById(userPO) > 0;
    }

    @Override
    public List<UserVO> listUser() {
        List<UserPO> userPOS = baseMapper.selectList(null);
        List<UserVO> userList = userPOS.stream().map(user -> {
            UserVO userVO = new UserVO();
            try {
                BeanUtils.copyProperties(user, userVO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return userVO;
        }).collect(Collectors.toList());
        return userList;
    }
}
