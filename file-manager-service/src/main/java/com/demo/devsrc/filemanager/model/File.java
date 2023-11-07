package com.demo.devsrc.filemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "files", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "file_uk")})
@Getter
@Setter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String name;

    @Column(name = "content_type", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String contentType;

    @Column(name = "data", nullable = false)
    @Lob
    @NotNull
    @JsonIgnore
    private byte[] data;

    public File(String name, String contentType, byte[] data) {
        this(null, name, contentType, data);
    }

    public File(Integer id, String name, String contentType, byte[] data) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File that = (File) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
