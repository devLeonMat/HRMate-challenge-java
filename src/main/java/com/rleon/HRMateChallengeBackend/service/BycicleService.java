package com.rleon.HRMateChallengeBackend.service;

import com.rleon.HRMateChallengeBackend.application.dto.BycicleResponse;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface BycicleService {

    List<BycicleResponse> getAll(String searchLocation, String date, String dateStart, String dateEnd) throws FileNotFoundException, ParseException;
}
