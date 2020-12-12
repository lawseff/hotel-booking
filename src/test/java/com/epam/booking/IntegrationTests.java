package com.epam.booking;

import com.epam.booking.command.factory.CommandFactoryImpl;
import com.epam.booking.command.impl.CommandTestUtils;
import com.epam.booking.connection.ConnectionPool;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ConnectionPoolException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
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
  public static void tearDown() throws ConnectionPoolException {
    ConnectionPool.getInstance().close();
  }

  private HttpServletRequest request;
  private Map<String, Object> requestAttributes;
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
    response = mock(HttpServletResponse.class);
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
    connection.close();
  }

  private static int countRows(String table, String where) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_booking_test?user=root&password=root");
    String query = "SELECT COUNT(*) FROM " + table + " WHERE " + where + ";";
    ResultSet resultSet = connection.createStatement().executeQuery(query);
    resultSet.next();
    return resultSet.getInt(1);
  }

  private static void cleanUp(String query) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_booking_test?user=root&password=root");
    connection.createStatement().executeUpdate(query);
  }

}
