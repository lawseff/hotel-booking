package com.epam.booking.command.factory;

import com.epam.booking.builder.Builder;
import com.epam.booking.builder.impl.ReservationBuilder;
import com.epam.booking.builder.impl.RoomBuilder;
import com.epam.booking.builder.impl.RoomClassBuilder;
import com.epam.booking.builder.impl.UserBuilder;
import com.epam.booking.command.impl.ChangeLanguageCommand;
import com.epam.booking.command.impl.ChangeRoomStatusCommand;
import com.epam.booking.command.impl.LoginCommand;
import com.epam.booking.command.impl.SavePricesCommand;
import com.epam.booking.command.impl.ShowPageCommand;
import com.epam.booking.command.impl.SignOutCommand;
import com.epam.booking.command.impl.UpdatePageCommand;
import com.epam.booking.command.impl.reservation.ApproveCommand;
import com.epam.booking.command.impl.reservation.BookCommand;
import com.epam.booking.command.impl.reservation.CancelReservationCommand;
import com.epam.booking.command.impl.reservation.PayCommand;
import com.epam.booking.command.impl.reservation.SetCheckedInCommand;
import com.epam.booking.command.impl.reservation.SetCheckedOutCommand;
import com.epam.booking.command.impl.reservation.ShowReservationsPageCommand;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.service.api.RoomService;
import com.epam.booking.service.api.UserService;
import com.epam.booking.command.Command;
import com.epam.booking.service.impl.ReservationServiceImpl;
import com.epam.booking.service.impl.RoomClassServiceImpl;
import com.epam.booking.service.impl.RoomServiceImpl;
import com.epam.booking.service.impl.UserServiceImpl;
import com.epam.booking.utils.DateUtils;
import com.epam.booking.utils.DaysCalculator;
import com.epam.booking.utils.PriceCalculator;
import com.epam.booking.utils.RoomPicker;
import com.epam.booking.utils.data.loader.PageDataLoader;
import com.epam.booking.utils.data.loader.impl.BookPageDataLoader;
import com.epam.booking.utils.data.loader.impl.RoomsPageDataLoader;
import com.epam.booking.dao.DaoFactory;
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.validation.impl.BookingDetailsValidatorImpl;
import com.epam.booking.validation.impl.PriceValidatorImpl;

public class CommandFactoryImpl implements CommandFactory {

    // admin commands
    public static final String SAVE_PRICES_COMMAND = "save_prices";
    public static final String CHANGE_ROOM_STATUS_COMMAND = "change_room_status";
    public static final String APPROVE_COMMAND = "approve";
    public static final String SET_CHECKED_IN_COMMAND = "set_checked_in";
    public static final String SET_CHECKED_OUT_COMMAND = "set_checked_out";
    public static final String SHOW_ROOMS_PAGE_COMMAND = "show_rooms_page";

    // user commands
    public static final String BOOK_COMMAND = "book";
    public static final String PAY_COMMAND = "pay";
    public static final String CANCEL_RESERVATION_COMMAND = "cancel_reservation";
    public static final String SIGN_OUT_COMMAND = "sign_out";
    public static final String SHOW_BOOK_PAGE_COMMAND = "show_book_page";
    public static final String SHOW_RESERVATIONS_PAGE_COMMAND = "show_reservations_page";

    // any credentials commands
    public static final String SHOW_HOME_PAGE_COMMAND = "show_home_page";
    public static final String SHOW_LOGIN_PAGE_COMMAND = "show_login_page";
    public static final String LOGIN_COMMAND = "login";
    public static final String CHANGE_LANGUAGE_COMMAND = "change_language";
    public static final String UPDATE_PAGE_COMMAND = "update_page";

    // URLs
    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_PAGE_URL = "/login";
    private static final String BOOK_PAGE_URL = "/book";
    private static final String ROOMS_PAGE_URL = "/rooms";

    private DaoFactory daoFactory;

    public CommandFactoryImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Command createCommand(String commandName) {
        Command command;
        switch (commandName) {
            case SHOW_HOME_PAGE_COMMAND:
                command = new ShowPageCommand(HOME_PAGE_URL);
                break;
            case SHOW_LOGIN_PAGE_COMMAND:
                command = new ShowPageCommand(LOGIN_PAGE_URL);
                break;
            case LOGIN_COMMAND:
                command = new LoginCommand(
                        getUserService()
                );
                break;
            case SIGN_OUT_COMMAND:
                command = new SignOutCommand();
                break;
            case BOOK_COMMAND:
                command = new BookCommand(
                        getRoomClassService(),
                        getReservationService(),
                        new BookingDetailsValidatorImpl(new DateUtils())
                );
                break;
            case SHOW_BOOK_PAGE_COMMAND:
                command = new ShowPageCommand(
                        BOOK_PAGE_URL,
                        getBookPageDataLoader()
                );
                break;
            case SHOW_ROOMS_PAGE_COMMAND:
                command = new ShowPageCommand(
                        ROOMS_PAGE_URL,
                        getRoomsPageDataLoader()
                );
                break;
            case SAVE_PRICES_COMMAND:
                command = new SavePricesCommand(
                        getRoomClassService(),
                        getRoomsPageDataLoader(),
                        new PriceValidatorImpl()
                );
                break;
            case CHANGE_ROOM_STATUS_COMMAND:
                command = new ChangeRoomStatusCommand(
                      getRoomService(),
                      getRoomsPageDataLoader()
                );
                break;
            case SHOW_RESERVATIONS_PAGE_COMMAND:
                command = new ShowReservationsPageCommand(
                        getReservationService(),
                        getRoomService(),
                        new DaysCalculator(),
                        new PriceCalculator(),
                        new RoomPicker(new DateUtils())
                );
                break;
            case CANCEL_RESERVATION_COMMAND:
                command = new CancelReservationCommand(
                        getReservationService()
                );
                break;
            case APPROVE_COMMAND:
                command = new ApproveCommand(
                        getRoomService(),
                        getReservationService(),
                        new PriceValidatorImpl()
                );
                break;
            case PAY_COMMAND:
                command = new PayCommand(
                        getReservationService()
                );
                break;
            case SET_CHECKED_IN_COMMAND:
                command = new SetCheckedInCommand(
                        getReservationService()
                );
                break;
            case SET_CHECKED_OUT_COMMAND:
                command = new SetCheckedOutCommand(
                      getReservationService()
                );
                break;
            case CHANGE_LANGUAGE_COMMAND:
                command = new ChangeLanguageCommand();
                break;
            case UPDATE_PAGE_COMMAND:
                command = new UpdatePageCommand();
                break;
            default:
                throw new IllegalArgumentException("Invalid command: " + commandName);
        }
        return command;
    }

    private UserService getUserService() {
        Builder<User> builder = new UserBuilder();
        return new UserServiceImpl(daoFactory, builder);
    }

    private RoomService getRoomService() {
        Builder<Room> builder = new RoomBuilder();
        return new RoomServiceImpl(daoFactory, builder);
    }

    private RoomClassService getRoomClassService() {
        Builder<RoomClass> builder = new RoomClassBuilder();
        return new RoomClassServiceImpl(daoFactory, builder);
    }

    private ReservationService getReservationService() {
        Builder<Reservation> builder = new ReservationBuilder();
        return new ReservationServiceImpl(daoFactory, builder);
    }

    private PageDataLoader getBookPageDataLoader() {
        RoomClassService roomClassService = getRoomClassService();
        return new BookPageDataLoader(roomClassService);
    }

    private PageDataLoader getRoomsPageDataLoader() {
        RoomService roomService = getRoomService();
        RoomClassService roomClassService = getRoomClassService();
        return new RoomsPageDataLoader(roomClassService, roomService);
    }

}
