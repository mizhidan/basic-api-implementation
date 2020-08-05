package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();
  User admin = new User("admin", "male", 22, "abc@bcd.com", "12345678911");
  private UserController userController = new UserController();

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEventList = new ArrayList<>();
    rsEventList.add(new RsEvent("第一条事件", "无标签", admin));
    rsEventList.add(new RsEvent("第二条事件", "无标签", admin));
    rsEventList.add(new RsEvent("第三条事件", "无标签", admin));
    return rsEventList;
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index) {
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null || end != null) {
      return ResponseEntity.ok(rsList.subList(start - 1, end));
    }
    return ResponseEntity.ok(rsList);
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
    userController.initUserList();
    if(!isUserExisted(rsEvent.getUser())) {
      userController.getUserList().add(rsEvent.getUser());
    }
    int index = rsList.size() - 1;
    return ResponseEntity.created(null).header(index + "").build();
  }

  public boolean isUserExisted(User user) {
    for(User existedUser : userController.getUserList()) {
      if (existedUser.getUsername().equals(user.getUsername())) {
        return true;
      }
    }
    return false;
  }

  @PostMapping("/rs/{index}")
  public ResponseEntity setRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
    if (rsEvent.getEventName() == null) {
      rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    } else if (rsEvent.getKeyWord() == null) {
      rsList.get(index - 1).setEventName(rsEvent.getEventName());
    } else {
      rsList.get(index - 1).setEventName(rsEvent.getEventName());
      rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    }
    return ResponseEntity.created(null).header((index - 1) + "").build();
  }

  @DeleteMapping("/rs/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
    return ResponseEntity.ok().build();
  }
}
