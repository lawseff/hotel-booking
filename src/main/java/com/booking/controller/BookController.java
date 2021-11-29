package com.booking.controller;

import com.booking.exception.ServiceException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.booking.service.BookService;
import com.booking.service.RoomService;

@Controller
@PreAuthorize("@roleService.isUser()")
public class BookController {

    private final RoomService roomService;
    private final BookService bookService;

    public BookController(RoomService roomService, BookService bookService) {
        this.roomService = roomService;
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String showBookPage(Model model) {
        roomService.setRooms(model);
        return "book";
    }

    @PostMapping("/book")
    public RedirectView bookRoom(
            @RequestParam("arrivalDate") Date arrivalDate,
            @RequestParam("departureDate") Date departureDate,
            @RequestParam("personsAmount") Integer personsAmount,
            @RequestParam("roomClass") String roomClass
    ) throws ServiceException {
        bookService.book(arrivalDate, departureDate, personsAmount, roomClass);
        return new RedirectView("/reservations");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
