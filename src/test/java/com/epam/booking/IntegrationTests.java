package com.epam.booking;

import com.epam.booking.command.factory.CommandFactoryImpl;
import com.epam.booking.command.impl.CommandTestUtils;
import com.epam.booking.connection.ConnectionPool;
import web.entity.User;
import web.entity.reservation.Reservation;
import web.entity.room.Room;
import web.entity.room.RoomClass;
import com.epam.booking.exception.ConnectionPoolException;
import com.epam.booking.exception.ServiceException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.*;
import org.junit.runner.RunWith;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class IntegrationTests {

  private Controller controller;

  @BeforeClass
  public static void setUpOnce() throws SQLException {
    createTestDatabase();
    ConnectionPool.getInstance(); // lazy init
  }

  @AfterClass
  public static void tearDownOnce() throws ConnectionPoolException {
    ConnectionPool.getInstance().close();
  }

  private HttpServletRequest request;
  private Map<String, Object> requestAttributes;
  private HttpSession session;
  private HttpServletResponse response;

  @Before
  public void setUp() {
    controller = spy(new Controller());
    ServletContext context = mock(ServletContext.class);
    when(context.getContextPath())
        .thenReturn("");
    when(context.getRequestDispatcher(any()))
        .thenReturn(mock(RequestDispatcher.class));
    doReturn(context).when(controller).getContext();

    request = mock(HttpServletRequest.class);
    requestAttributes = new HashMap<>();
    doAnswer(
        i -> requestAttributes.put((String)i.getArguments()[0], i.getArguments()[1])
    ).when(request).setAttribute(any(), any());
    CommandTestUtils.mockCurrentPage(request);
    session = mock(HttpSession.class);
    when(request.getSession())
        .thenReturn(session);

    response = mock(HttpServletResponse.class);

  }

  @After
  public void tearDOwn() throws SQLException {
    cleanUp("DELETE FROM reservation");
  }

  // =========================================================================

  @Test
  public void ShowBookPageCommand_Execute_RoomClassesObtained() throws ServletException, IOException {
    setCommand(CommandFactoryImpl.SHOW_BOOK_PAGE_COMMAND);

    controller.doGet(request, response);

    List<RoomClass> list = getAttribute("room_classes", List.class);
    assertEquals(3, list.size());
    assertTrue(list.stream().anyMatch(c -> "STANDARD".equals(c.getName())));
    assertTrue(list.stream().anyMatch(c -> "LUXE".equals(c.getName())));
    assertTrue(list.stream().anyMatch(c -> "VIP".equals(c.getName())));
  }

  @Test
  public void ShowRoomsPageCommand_Execute_RoomsAndClassesObtained() throws ServletException, IOException {
    setCommand(CommandFactoryImpl.SHOW_ROOMS_PAGE_COMMAND);

    controller.doGet(request, response);

    // room classes
    List<RoomClass> roomClasses = getAttribute("room_classes", List.class);
    assertEquals(3, roomClasses.size());
    assertTrue(roomClasses.stream().anyMatch(c -> "STANDARD".equals(c.getName())));
    assertTrue(roomClasses.stream().anyMatch(c -> "LUXE".equals(c.getName())));
    assertTrue(roomClasses.stream().anyMatch(c -> "VIP".equals(c.getName())));
    // rooms
    List<Room> rooms = getAttribute("rooms", List.class);
    assertEquals(1, rooms.size());
    assertEquals(1, rooms.get(0).getBedsAmount());
    assertEquals("VIP", rooms.get(0).getRoomClass().getName());
    assertTrue(rooms.get(0).isActive());
  }

  @DataProvider
  public static Object[][] changeRoomStatusCommand() {
    return new Object[][] {
        {"checked", 1 },
        { null, 0 }
    };
  }
  @Test
  @UseDataProvider("changeRoomStatusCommand")
  public void ChangeRoomStatusCommand_Params_StatusChanged(String checkbox, int expected) throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.CHANGE_ROOM_STATUS_COMMAND);
    mockParam("id", "1");
    mockParam("checkbox", checkbox);

    controller.doPost(request, response);

    assertEquals(1, countRows("room", "id=1 AND is_active=" + expected));
    cleanUp("UPDATE room SET is_active=1 WHERE id=1");
  }

  @Test
  public void SavePricesCommand_NormalFlow_PricesUpdated() throws ServletException, IOException, SQLException {
    setCommand(CommandFactoryImpl.SAVE_PRICES_COMMAND);
    when(request.getParameterValues("name"))
        .thenReturn(new String[]{"VIP"});
    when(request.getParameterValues("basic_rate"))
        .thenReturn(new String[]{"150.50"});
    when(request.getParameterValues("rate_per_person"))
        .thenReturn(new String[]{"250.50"});

    controller.doPost(request, response);

    assertEquals(1, countRows("room_class",
        "class_name='VIP' AND basic_rate=150.50 AND rate_per_person=250.50"
    ));
    cleanUp("UPDATE room_class " +
        "SET basic_rate=145.00, rate_per_person=25.00 " +
        "WHERE class_name='VIP'"
    );
  }

  @DataProvider
  public static Object[][] savePricesCommand() {
    return new Object[][] {
        {"-150.50", "250.50"}, // invalid basicRate
        {"150.50", "10000.00"} // invalid ratePerPerson
    };
  }
  @Test
  @UseDataProvider("savePricesCommand")
  public void SavePricesCommand_InvalidPrice_Exception(String basicRate, String ratePerPerson) throws ServletException, IOException {
    setCommand(CommandFactoryImpl.SAVE_PRICES_COMMAND);
    when(request.getParameterValues("name"))
        .thenReturn(new String[]{"VIP"});
    when(request.getParameterValues("basic_rate"))
        .thenReturn(new String[]{basicRate});
    when(request.getParameterValues("rate_per_person"))
        .thenReturn(new String[]{ratePerPerson});

    try {
      controller.doPost(request, response);
    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof  ServiceException);
    }
  }

  @Test
  public void BookCommand_ValidParams_NormalFlow() throws ServletException, IOException, SQLException {
    setCommand(CommandFactoryImpl.BOOK_COMMAND);
    setUserRole();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String arrivalDate = format.format(Date.from(LocalDate.now().plusDays(5).atStartOfDay().toInstant(ZoneOffset.UTC)));
    mockParam("arrival_date", arrivalDate);
    String departureDate = format.format(Date.from(LocalDate.now().plusDays(6).atStartOfDay().toInstant(ZoneOffset.UTC)));
    mockParam("departure_date", departureDate);
    mockParam("room_class", "VIP");
    mockParam("persons_amount", "1");

    controller.doPost(request, response);

    assertEquals(1, countRows("reservation",
        "arrival_date='" + arrivalDate + "' AND departure_date='" + departureDate + "'"
    ));
  }

  @DataProvider
  public static Object[][] bookCommand_InvalidParams() {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String validArrivalDate = format.format(Date.from(LocalDate.now().plusDays(5).atStartOfDay().toInstant(ZoneOffset.UTC)));
    String validDepartureDate = format.format(Date.from(LocalDate.now().plusDays(6).atStartOfDay().toInstant(ZoneOffset.UTC)));
    String invalidDate = format.format(Date.from(LocalDate.now().minusYears(3).atStartOfDay().toInstant(ZoneOffset.UTC)));
    return new Object[][] {
        {"", "", "VIP", "1"}, // unparsable
        {validArrivalDate, validDepartureDate, "VIP", "-1"}, // invalid persons amount
        {validArrivalDate, validDepartureDate, "INVALID_CLASS", "1"},

        // invalid period
        {validArrivalDate, invalidDate, "VIP", "1"},
        {invalidDate, validArrivalDate, "VIP", "1"},
    };
  }
  @Test
  @UseDataProvider("bookCommand_InvalidParams")
  public void BookCommand_InvalidParamsException(
      String arrivalDate,
      String departureDate,
      String roomClass,
      String personsAmount
  ) throws ServletException, IOException {
    setCommand(CommandFactoryImpl.BOOK_COMMAND);
    setUserRole();
    mockParam("arrival_date", arrivalDate);
    mockParam("departure_date", departureDate);
    mockParam("room_class", roomClass);
    mockParam("persons_amount", personsAmount);

    try {
      controller.doPost(request, response);
    } catch (ServletException e) {

      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void SetCheckedInCommand_ValidStatus_CheckedIn() throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.CHECK_IN_COMMAND);
    initReservation("PAID");
    mockParam("id", "1");

    controller.doPost(request, response);

    assertEquals(1, countRows("reservation", "reservation_status='CHECKED_IN'"));
  }

  @Test
  public void SetCheckedInCommand_InvalidStatus_Exception() throws SQLException, IOException {
    setCommand(CommandFactoryImpl.CHECK_IN_COMMAND);
    initReservation("CANCELLED");
    mockParam("id", "1");

    try {
      controller.doPost(request, response);

    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void SetCheckedInCommand_InvalidId_Exception() throws SQLException, IOException {
    setCommand(CommandFactoryImpl.CHECK_IN_COMMAND);
    initReservation("PAID");
    mockParam("id", "999");

    try {
      controller.doPost(request, response);

    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void SetCheckedOutCommand_ValidStatus_CheckedOut() throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.CHECK_OUT_COMMAND);
    initReservation("CHECKED_IN");
    mockParam("id", "1");

    controller.doPost(request, response);

    assertEquals(1, countRows("reservation", "reservation_status='CHECKED_OUT'"));
  }

  @Test
  public void SetCheckedOutCommand_InvalidStatus_Exception() throws SQLException, IOException {
    setCommand(CommandFactoryImpl.CHECK_OUT_COMMAND);
    initReservation("CANCELLED");
    mockParam("id", "1");

    try {
      controller.doPost(request, response);

    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void CancelReservationCommand_ValidStatus_Cancelled() throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.CANCEL_RESERVATION_COMMAND);
    initReservation("WAITING");
    mockParam("id", "1");
    setUserRole();

    controller.doPost(request, response);

    assertEquals(1, countRows("reservation", "reservation_status='CANCELLED'"));
  }

  @Test
  public void CancelReservationCommand_InvalidStatis_Exception() throws SQLException, IOException {
    setCommand(CommandFactoryImpl.CANCEL_RESERVATION_COMMAND);
    initReservation("CANCELLED");
    mockParam("id", "1");
    setUserRole();

    try {
      controller.doPost(request, response);

    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void PayCommand_ValidParams_Paid() throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.PAY_COMMAND);
    setUserRole();
    initReservation("APPROVED");
    mockParam("id", "1");
    mockParam("card_number", "5500000000000004");
    DateFormat format = new SimpleDateFormat("MM/yy");
    String valid_thru = format.format(Date.from(LocalDate.now().plusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC)));
    mockParam("valid_thru", valid_thru);
    mockParam("cvv_number", "123");

    controller.doPost(request, response);

    assertEquals(1, countRows("reservation", "reservation_status='PAID'"));
  }

  @DataProvider
  public static Object[][] payCommand_InvalidParams() {
    DateFormat format = new SimpleDateFormat("MM/yy");
    String validExpirationDate = format.format(Date.from(LocalDate.now().plusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC)));
    String invalidExpirationDate = format.format(Date.from(LocalDate.now().minusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC)));
    return new Object[][] {
        {"WAITING", "5500000000000004", validExpirationDate, "123"}, // invalid status
        {"APPROVED", "4444", validExpirationDate, "123"}, // invalid card number
        {"APPROVED", "5500000000000004", invalidExpirationDate, "123"},
        {"APPROVED", "5500000000000004", validExpirationDate, "99999"}, // invalid cvv
    };
  }
  @Test
  @UseDataProvider("payCommand_InvalidParams")
  public void PayCommand_InvalidParams_Exception(
      String status, String cardNumber, String validThru, String cvvNumber
  ) throws SQLException, IOException {
    setCommand(CommandFactoryImpl.PAY_COMMAND);
    setUserRole();
    initReservation(status);
    mockParam("id", "1");
    mockParam("card_number", cardNumber);
    mockParam("valid_thru", validThru);
    mockParam("cvv_number", cvvNumber);

    try {
      controller.doPost(request, response);
    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @Test
  public void PayCommand_NotAuthorized_Exception() throws SQLException, IOException {
    setCommand(CommandFactoryImpl.PAY_COMMAND);
    setUserRole();
    initReservation("2", "APPROVED");
    mockParam("id", "1");
    mockParam("card_number", "5500000000000004");
    DateFormat format = new SimpleDateFormat("MM/yy");
    String valid_thru = format.format(Date.from(LocalDate.now().plusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC)));
    mockParam("valid_thru", valid_thru);
    mockParam("cvv_number", "123");

    try {
      controller.doPost(request, response);
    } catch (ServletException e) {
      assertTrue(e.getCause() instanceof ServiceException);
    }
  }

  @DataProvider
  public static Object[][] showReservationsPageCommand() {
    return new Object[][] {
        {true, "1", "WAITING"},
        {true, "1", "PAID"},
        {false, "1", "WAITING"},
        {true, null, "PAID"},
        {false, null, "WAITING"},
    };
  }
  @Test
  @UseDataProvider("showReservationsPageCommand")
  public void ShowReservationsPageCommand(boolean isAdmin, String id, String status) throws SQLException, ServletException, IOException {
    setCommand(CommandFactoryImpl.SHOW_RESERVATIONS_PAGE_COMMAND);
    if (isAdmin) {
      setAdminRole();
    } else {
      setUserRole();
    }
    initReservation(status);
    mockParam("id", id);

    controller.doGet(request, response);

    List<Reservation> reservations = getAttribute("reservations", List.class);
    assertEquals(1, reservations.size());
    if (id != null) {
      assertNotNull(getAttribute("reservation_details", Reservation.class));
    } else {
      assertNull(request.getAttribute("reservation_details"));
    }
  }

  // =========================================================================

  private void mockParam(String param, String value) {
    when(request.getParameter(param))
        .thenReturn(value);
  }

  private void setCommand(String command) {
    mockParam("command", command);
  }

  private <T> T getAttribute(String name, Class<T> clazz) {
    Object attribute = requestAttributes.get(name);
    assertNotNull(attribute);
    return (T) attribute;
  }

  private void setUserRole() {
    when(session.getAttribute("user")).thenReturn(new User(
        1,
        false,
        "test@example.com",
        "qwerty",
        "Test",
        "User"
    ));
  }

  private void setAdminRole() {
    when(session.getAttribute("user")).thenReturn(new User(
        2,
        true,
        "admin@example.com",
        "admin",
        "Test",
        "Admin"
    ));
  }

  private void initReservation(String status) throws SQLException {
    initReservation("1", status);
  }

  private void initReservation(String userId, String status) throws SQLException {
    String query = "INSERT INTO reservation " +
        "(id, user_id, room_class_id, room_id, reservation_status, arrival_date, departure_date, persons_amount, total_price) " +
        "VALUES (1, %s, 1, 1, '%s', '2020-12-10', '2020-12-15', 1, '500.00');";
    executeUpdate(String.format(query,
        userId,
        status
    ));
  }

  private static void createTestDatabase() throws SQLException {
    Connection createConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=root");
    Statement createStatement = createConnection.createStatement();
    try {
      createStatement.executeUpdate(
          "DROP DATABASE IF EXISTS hotel_booking_test;"
      );
    } catch (SQLSyntaxErrorException e) {
      // no database
    }
    createStatement.executeUpdate("CREATE DATABASE hotel_booking_test");
    createConnection.close();

    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_booking_test?user=root&password=root");
    Statement statement = connection.createStatement();
    statement.executeUpdate("CREATE TABLE booking_user (\n" +
        "\tid int NOT NULL AUTO_INCREMENT,\n" +
        "    is_admin bool NOT NULL DEFAULT false,\n" +
        "    email varchar(255) NOT NULL UNIQUE,\n" +
        "    user_password varchar(255) NOT NULL,\n" +
        "    first_name varchar(100) NOT NULL,\n" +
        "    second_name varchar(100) NOT NULL,\n" +
        "    \n" +
        "    PRIMARY KEY (id)\n" +
        ");"
    );
    statement.executeUpdate("CREATE TABLE room_class (\n" +
        "\tid int NOT NULL AUTO_INCREMENT,\n" +
        "    class_name varchar(100) NOT NULL UNIQUE,\n" +
        "    \n" +
        "    /* per night */\n" +
        "    basic_rate decimal(10,2) NOT NULL,\n" +
        "    rate_per_person decimal(10,2) NOT NULL,\n" +
        "    \n" +
        "    PRIMARY KEY (id)\n" +
        ");"
    );
    statement.executeUpdate("CREATE TABLE room (\n" +
        "\tid int NOT NULL AUTO_INCREMENT,\n" +
        "    is_active bool NOT NULL DEFAULT true,\n" +
        "    room_class_id int NOT NULL,\n" +
        "    beds_amount tinyint NOT NULL,\n" +
        "    \n" +
        "    PRIMARY KEY (id),\n" +
        "    FOREIGN KEY (room_class_id) REFERENCES room_class(id)\n" +
        ");"
    );
    statement.executeUpdate("CREATE TABLE reservation (\n" +
        "\tid int NOT NULL AUTO_INCREMENT,\n" +
        "    user_id int NOT NULL,\n" +
        "    room_class_id int NOT NULL,\n" +
        "    room_id int,\n" +
        "    reservation_status enum('WAITING', 'APPROVED', 'PAID', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED') NOT NULL,\n" +
        "    arrival_date date NOT NULL,\n" +
        "    departure_date date NOT NULL,\n" +
        "    persons_amount tinyint NOT NULL,\n" +
        "    total_price decimal(10,2),\n" +
        "    \n" +
        "    PRIMARY KEY (id),\n" +
        "    FOREIGN KEY (user_id) REFERENCES booking_user(id),\n" +
        "    FOREIGN KEY (room_class_id) REFERENCES room_class(id),\n" +
        "    FOREIGN KEY (room_id) REFERENCES room(id)\n" +
        ");"
    );
    statement.executeUpdate("INSERT INTO room_class (class_name, basic_rate, rate_per_person)\n" +
        "\tVALUES ('STANDARD', 80, 15), ('LUXE', 120, 20), ('VIP', 145, 25);"
    );
    statement.executeUpdate("INSERT INTO room (room_class_id, beds_amount)\n" +
        "\tVALUES \n" +
        "    ((SELECT id FROM room_class WHERE class_name='VIP'), 1);");
    statement.executeUpdate("INSERT INTO booking_user(email, user_password, first_name, second_name) " +
        "VALUES ('test@example.com', 'qwerty', 'Test', 'User')");
    statement.executeUpdate("INSERT INTO booking_user(email, user_password, first_name, second_name) " +
        "VALUES ('admin@example.com', 'admin', 'Test', 'Admin')");
    connection.close();
  }

  private static int countRows(String table, String where) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_booking_test?user=root&password=root");
    String query = "SELECT COUNT(*) FROM " + table + " WHERE " + where + ";";
    ResultSet resultSet = connection.createStatement().executeQuery(query);
    resultSet.next();
    int result = resultSet.getInt(1);
    connection.close();
    return result;
  }

  private static void cleanUp(String query) throws SQLException {
    executeUpdate(query);
  }

  private static void executeUpdate(String query) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_booking_test?user=root&password=root");
    connection.createStatement().executeUpdate(query);
    connection.close();
  }

}
