package com.rleon.HRMateChallengeBackend.application.controller;


import com.rleon.HRMateChallengeBackend.application.dto.BycicleResponse;
import com.rleon.HRMateChallengeBackend.service.BycicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/availability")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BycicleController {

    private BycicleService bycicleService;

    @GetMapping
    public ResponseEntity<List<BycicleResponse>> getAllByciclesWithParams(@RequestParam(value = "search_location", required = false) String searchLocation, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "date_start", required = false) String dateStart, @RequestParam(value = "date_end", required = false) String dateEnd) throws FileNotFoundException, ParseException {
        log.info("[BycicleController:getAllByciclesWithParams], Params[ {}, {}, {},{}]", searchLocation, date, dateStart, dateEnd);
        return ResponseEntity.ok().body(bycicleService.getAll(searchLocation, date, dateStart, dateEnd));
    }

}
