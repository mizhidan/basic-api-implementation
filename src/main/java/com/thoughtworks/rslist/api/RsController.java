package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();
  private UserController userController = new UserController();
  User admin = new User("admin", "male", 22, "abc@bcd.com", "12345678911");

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEventList = new ArrayList<>();
    rsEventList.add(new RsEvent("第一条事件", "无标签", admin));
    rsEventList.add(new RsEvent("第二条事件", "无标签", admin));
    rsEventList.add(new RsEvent("第三条事件", "无标签", admin));
    return rsEventList;
  }

  @GetMapping("/rs/{index}")
  public RsEvent getRsEvent(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null || end != null) {
      return rsList.subList(start - 1, end);
    }
    return rsList;
  }

  @PostMapping("/rs/event")
  public void addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
    if(!userController.getUserList().contains(rsEvent.getUser())) {
      userController.getUserList().add(rsEvent.getUser());
    }
  }

  @PostMapping("/rs/{index}")
  public void setRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
    if (rsEvent.getEventName() == null) {
      rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    } else if (rsEvent.getKeyWord() == null) {
      rsList.get(index - 1).setEventName(rsEvent.getEventName());
    } else {
      rsList.get(index - 1).setEventName(rsEvent.getEventName());
      rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    }
  }

  @DeleteMapping("/rs/{index}")
  public void deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
  }
}
