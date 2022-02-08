package com.amdocs.backend;

import com.amdocs.backend.exception.InvalidDateException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class SLAAnalyzer 
{
    /**
    * Method will receive a particular problem opening date and amount of working hours (business hours) it should be solved and return the maximum date and time it should be solved.
    * It should be considered:
    * 	- Business hours are from 8AM to 5PM excluding weekends and holidays
    *  - Logic should consider only month of August 2019, Sao Carlos location
    *  - Method signature cannot be changed
    *  
    * @param iOpeningDateTime - Problem opening date
    * @param iSLA - Quantity of hours to solve the problem
    * @return Maximum date and time that problem should be solved
    */
    
    public LocalDateTime calculateSLA(LocalDateTime iOpeningDateTime, Integer iSLA){

        // Validating if the data is in range
        try {
            if ((iOpeningDateTime.getYear() != 2019) || (iOpeningDateTime.getMonth() != Month.AUGUST))
            throw new InvalidDateException("Data inválida, insira uma data do mês de Agosto/2019");
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        // Creating time limit of a Business Day
        LocalDateTime endBusinessDay = LocalDateTime.parse(LocalDateTime.of(
                iOpeningDateTime.getYear(),
                iOpeningDateTime.getMonth(),
                iOpeningDateTime.getDayOfMonth(),
                17,
                0,
                0,
                0
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));

        // Creating start of a Business Day
        LocalDateTime startBusinessDay = LocalDateTime.parse(LocalDateTime.of(
                iOpeningDateTime.getYear(),
                iOpeningDateTime.getMonth(),
                iOpeningDateTime.getDayOfMonth(),
                8,
                0,
                0,
                0
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));

        // Validating SLA time
        if (iOpeningDateTime.getDayOfWeek() == DayOfWeek.SATURDAY)
            return startBusinessDay.plusDays(2).plusHours(iSLA);
        else if (iOpeningDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
            return startBusinessDay.plusDays(1).plusHours(iSLA);
        else if (iOpeningDateTime.getDayOfMonth() == 15)
            return startBusinessDay.plusDays(1).plusHours(iSLA);
        else if(iOpeningDateTime.plusHours(iSLA).isAfter(endBusinessDay))
        {
            if(iOpeningDateTime.plusDays(1).getDayOfWeek() == DayOfWeek.SATURDAY){
                Duration diff = Duration.between(endBusinessDay, iOpeningDateTime.plusHours(iSLA));
                return startBusinessDay.plusDays(3).plusSeconds(diff.getSeconds());
            }
            else if (iOpeningDateTime.plusDays(1).getDayOfMonth() == 15) {
                Duration diff = Duration.between(endBusinessDay, iOpeningDateTime.plusHours(iSLA));
                return startBusinessDay.plusDays(2).plusSeconds(diff.getSeconds());
            }
            else{
                Duration diff = Duration.between(endBusinessDay, iOpeningDateTime.plusHours(iSLA));
                return startBusinessDay.plusDays(1).plusSeconds(diff.getSeconds());
            }
        }
        return iOpeningDateTime.plusHours(iSLA);
    }

}
