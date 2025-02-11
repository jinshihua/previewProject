package preview.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Boolean locked;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private LocalDateTime lastLoginTime;

    private Long roleId;
}