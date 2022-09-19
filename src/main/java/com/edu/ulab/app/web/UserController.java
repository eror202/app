package com.edu.ulab.app.web;

import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.web.constant.WebConstant;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.update.BookToListRequest;
import com.edu.ulab.app.web.request.update.UpdatedUserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

import static com.edu.ulab.app.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserDataFacade userDataFacade;

    public UserController(UserDataFacade userDataFacade) {
        this.userDataFacade = userDataFacade;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse createUserWithBooks(@RequestBody UserBookRequest request,
                                                @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }


    @PutMapping(value = "/update")
    @Operation(summary = "update user with books. ",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse updateUserWithBooks(@RequestBody UpdatedUserBookRequest request) {
        UserBookResponse response = userDataFacade.updateUserWithBooks(request);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Get user with books.")
    public UserBookResponse getUserWithBooks(@PathVariable Long userId) {
        UserBookResponse response = userDataFacade.getUserWithBooks(userId);
        log.info("Response with user and his books: {}", response);
        return response;
    }

    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Delete user with books. ")
    public ResponseEntity<HttpStatus> deleteUserWithBooks(@PathVariable Long userId) {
        log.info("Delete user and his books:  userId {}", userId);
        userDataFacade.deleteUserWithBooks(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/updateBookList")
    @Operation(summary = "update user's book list. ",
            responses = {
                    @ApiResponse(description = "Book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookToListRequest.class)))})
    public UserBookResponse addBookToBookList(@RequestBody BookToListRequest book){
        UserBookResponse response = userDataFacade.addBookToUserList(book);
        log.info("Response user with updated book list: {}", response);
        return response;
    }

    @DeleteMapping("/deleteBook/{bookId}")
    @Operation(summary = "delete book")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long bookId){
        log.info("Delete book in storage: {}");
        userDataFacade.deleteBook(bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
