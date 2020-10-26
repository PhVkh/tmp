package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.service.BenefactorService;
import fi.lipp.greatheart.directory.service.dto.BenefactorDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("benefactors")
public class BenefactorHandler {

    @Autowired
    private BenefactorService benefactorService;

    @PostMapping()
    public ResponseEntity<String> addEmployee(@RequestBody BenefactorDto benefactorDto) {
        benefactorService.save(benefactorDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BenefactorDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(benefactorService.findAll(pageable), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> updateEmployee(@RequestBody BenefactorDto dto) throws EntityNotFoundException {
        benefactorService.update(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteEmployee(@RequestBody Long id) throws EntityNotFoundException {
        benefactorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
