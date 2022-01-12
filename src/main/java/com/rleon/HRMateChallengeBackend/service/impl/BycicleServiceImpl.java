package com.rleon.HRMateChallengeBackend.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rleon.HRMateChallengeBackend.application.dto.BycicleResponse;
import com.rleon.HRMateChallengeBackend.infrastructure.exception.custom.BadRequestException;
import com.rleon.HRMateChallengeBackend.service.BycicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BycicleServiceImpl implements BycicleService {

    private static final Type REVIEW_TYPE = new TypeToken<List<BycicleResponse>>() {
    }.getType();

    @Override
    public List<BycicleResponse> getAll(String searchLocation, String date, String dateStart, String dateEnd) throws FileNotFoundException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Gson gson = new Gson();
        String path = "G:\\CHALLENGE\\HRMate-challenge-java\\src\\main\\resources\\DB.json";
        JsonReader reader = new JsonReader(new FileReader(path));
        List<BycicleResponse> data = gson.fromJson(reader, REVIEW_TYPE);
        if (date != null) {
            Date exactDate = format.parse(date);
            data = data.stream()
                    .filter(bycicleResponse -> bycicleResponse.getDateAvailable().compareTo(exactDate) == 0)
                    .filter(bycicleResponse -> {
                        boolean b = true;
                        if (searchLocation != null && searchLocation.length() > 0) {
                            b = bycicleResponse.getLocation().toLowerCase(Locale.ROOT).contains(searchLocation.toLowerCase(Locale.ROOT));
                        }
                        return b;
                    })
                    .collect(Collectors.toList());
        } else if (dateStart != null && dateEnd != null) {
            Date startDateToRent = format.parse(dateStart);
            Date endDateToRent = format.parse(dateEnd);
            if (startDateToRent.compareTo(endDateToRent) > 0) {
                throw new BadRequestException("error in range of dates");
            }
            data = data.stream().filter(bycicleResponse -> bycicleResponse.getDateAvailable().compareTo(startDateToRent) >= 0).filter(bycicleResponse -> {
                boolean b = true;
                if (startDateToRent.compareTo(endDateToRent) == 0) {
                    b = bycicleResponse.getDateAvailable().compareTo(endDateToRent) == 0;
                }
                return b;
            }).filter(bycicleResponse -> {
                boolean b = true;
                if (searchLocation != null && searchLocation.length() > 0) {
                    b = bycicleResponse.getLocation().toLowerCase(Locale.ROOT).contains(searchLocation.toLowerCase(Locale.ROOT));
                }
                return b;
            }).collect(Collectors.toList());
        }
        return data;
    }
}
