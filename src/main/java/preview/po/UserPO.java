package preview.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("users")
@EqualsAndHashCode(callSuper = false)
public class UserPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String nickname;

    private String email;

    private String phone;

    private Boolean locked;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private LocalDateTime lastLoginTime;

    @TableField("role_id")
    private Long roleId;
}
