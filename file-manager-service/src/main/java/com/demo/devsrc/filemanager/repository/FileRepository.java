package com.demo.devsrc.filemanager.repository;

import com.demo.devsrc.filemanager.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface FileRepository extends JpaRepository<File, Integer> {
    Optional<File> getByName(String name);
}
