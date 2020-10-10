package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.service.EmployeeService;
import fi.lipp.greatheart.directory.service.dto.EmployeeDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeResource {

    //TODO загрузка, роли

    @Autowired private EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employee) {
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(employeeService.findAll(pageable), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employee) throws EntityNotFoundException {
        employeeService.update(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteEmployee(@RequestBody Long id) throws EntityNotFoundException {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
