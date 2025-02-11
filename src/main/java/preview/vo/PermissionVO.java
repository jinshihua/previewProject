package preview.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PermissionVO {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}