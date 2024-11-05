package com.example.myselectshop.controller;

import com.example.myselectshop.dto.FolderRequestDto;
import com.example.myselectshop.dto.FolderResponseDto;
import com.example.myselectshop.security.UserDetailsImpl;
import com.example.myselectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FolderController {

  private final FolderService folderService;

  @PostMapping("/folders")
  public void addFolders(@RequestBody FolderRequestDto folderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<String> folderNames = folderRequestDto.getFolderNames();
    folderService.addFolders(folderNames, userDetails.getUser());
  }

  @GetMapping("/folders")
  public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return folderService.getFolders(userDetails.getUser());
  }
}
