package preview.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("role_permissions")
@EqualsAndHashCode(callSuper = false)
public class RolePermissionPO {

    private Long roleId;

    private Long permissionId;
}