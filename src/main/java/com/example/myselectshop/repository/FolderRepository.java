package com.example.myselectshop.repository;

import com.example.myselectshop.entity.Folder;
import com.example.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
  List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);
  // select * from folder where user_id = ? and name in (?, ?, ?);

  List<Folder> findAllByUser(User user);
}
