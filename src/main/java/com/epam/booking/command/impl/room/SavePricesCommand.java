package com.epam.booking.command.impl.room;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.utils.CurrentPageGetter;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.validation.api.PriceValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SavePricesCommand implements Command {

    private static final String NAME_PARAMETER = "name";
    private static final String BASIC_RATE_PARAMETER = "basic_rate";
    private static final String RATE_PER_PERSON_PARAMETER = "rate_per_person";

    private RoomClassService roomClassService;
    private PriceValidator priceValidator;

    public SavePricesCommand(RoomClassService roomClassService, PriceValidator priceValidator) {
        this.roomClassService = roomClassService;
        this.priceValidator = priceValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<RoomClass> roomClasses = getRoomClassesFromRequest(request);
        roomClassService.updatePrices(roomClasses);

        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(currentPage);
    }

    private List<RoomClass> getRoomClassesFromRequest(HttpServletRequest request) throws ServiceException {
        String[] nameParameters = request.getParameterValues(NAME_PARAMETER);
        String[] basicRateParameters = request.getParameterValues(BASIC_RATE_PARAMETER);
        String[] ratePerPersonParameters = request.getParameterValues(RATE_PER_PERSON_PARAMETER);
        int size = nameParameters.length;

        List<RoomClass> roomClasses = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            BigDecimal basicRate = new BigDecimal(basicRateParameters[i]);
            if (!priceValidator.isPriceValid(basicRate)) {
                throw new ServiceException("Invalid basic rate: " + basicRate);
            }
            BigDecimal ratePerPerson = new BigDecimal(ratePerPersonParameters[i]);
            if (!priceValidator.isPriceValid(ratePerPerson)) {
                throw new ServiceException("Invalid rate per person: " + ratePerPerson);
            }
            String name = nameParameters[i];
            RoomClass roomClass = new RoomClass(name, basicRate, ratePerPerson);
            roomClasses.add(roomClass);
        }
        return roomClasses;
    }

}
