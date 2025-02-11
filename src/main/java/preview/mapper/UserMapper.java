package preview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import preview.po.UserPO;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
