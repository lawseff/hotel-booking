package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.utils.CurrentPageGetter;
import com.epam.booking.utils.data.loader.PageDataLoader;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.validation.api.PriceValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class SavePricesCommand implements Command {

    private static final String ID_PARAMETER = "id";
    private static final String BASIC_RATE_PARAMETER = "basic_rate";
    private static final String RATE_PER_PERSON_PARAMETER = "rate_per_person";

    private RoomClassService roomClassService;
    private PageDataLoader roomsPageDataLoader;
    private PriceValidator priceValidator;

    public SavePricesCommand(RoomClassService roomClassService,
                             PageDataLoader roomsPageDataLoader, PriceValidator priceValidator) {
        this.roomClassService = roomClassService;
        this.roomsPageDataLoader = roomsPageDataLoader;
        this.priceValidator = priceValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String[] idParameters = request.getParameterValues(ID_PARAMETER);
        String[] basicRateParameters = request.getParameterValues(BASIC_RATE_PARAMETER);
        String[] ratePerPersonParameters = request.getParameterValues(RATE_PER_PERSON_PARAMETER);

        for (int i = 0; i < idParameters.length; i++) {
            int id = Integer.parseInt(idParameters[i]);

            BigDecimal basicRate = new BigDecimal(basicRateParameters[i]);
            if (!priceValidator.isPriceValid(basicRate)) {
                throw new ServiceException("Invalid basic rate: " + basicRate);
            }

            BigDecimal ratePerPerson = new BigDecimal(ratePerPersonParameters[i]);
            if (!priceValidator.isPriceValid(ratePerPerson)) {
                throw new ServiceException("Invalid rate per person: " + ratePerPerson);
            }

            roomClassService.updatePrices(id, basicRate, ratePerPerson);
        }

        roomsPageDataLoader.loadDataToSession(request);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(currentPage);
    }

}
