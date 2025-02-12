package preview.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import preview.po.UserPO;
import preview.vo.UserVO;

import java.util.List;

public interface UserService extends IService<UserPO> {
    UserVO getUserById(Long id);
    boolean createUser(UserPO userPO);
    boolean deleteUser(Long id);
    boolean updateUser(Long id,UserPO userPO);
    List<UserVO> listUser();
}
