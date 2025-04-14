package com.db.taskcrud.model;

import com.db.taskcrud.dto.response.ResponseTaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_status")
    private String status;

    public ResponseTaskDto toDto() {
        return ResponseTaskDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
