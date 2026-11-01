package com.example.sales;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/leads")
public class LeadController {
  private final Map<Long,Lead> leads = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(2);
  public LeadController() {
    leads.put(1L,new Lead(1L,"Aarav Sharma","aarav@example.com","Acme Ltd","NEW"));
    leads.put(2L,new Lead(2L,"Priya Patil","priya@example.com","Bright Tech","CONTACTED"));
  }
  @GetMapping public List<Lead> all() { return leads.values().stream().sorted(Comparator.comparing(Lead::getId)).toList(); }
  @GetMapping("/{id}") public Lead one(@PathVariable Long id) {
    Lead lead=leads.get(id); if(lead==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Lead not found"); return lead;
  }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) public Lead create(@RequestBody LeadRequest r) {
    if(r.getName()==null || r.getName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name is required");
    long id=sequence.incrementAndGet(); Lead lead=new Lead(id,r.getName(),r.getEmail(),r.getCompany(),r.getStatus()==null?"NEW":r.getStatus()); leads.put(id,lead); return lead;
  }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {
    if(leads.remove(id)==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Lead not found");
  }
}
