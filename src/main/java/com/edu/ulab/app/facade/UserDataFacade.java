package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.update.BookToListRequest;
import com.edu.ulab.app.web.request.update.UpdatedUserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UpdatedUserBookRequest userBookRequest) {
        UserDto userDto = userMapper.updatedUserRequestToUserDto(userBookRequest.getUserRequest());
        UserDto updatedUser = userService.updateUser(userDto);
        log.info("Updated user: {}", updatedUser);
        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::updatedBookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(updatedUser.getId()))
                .peek(mappedBookDto -> log.info("mapped Book: {}", mappedBookDto))
                .map(bookService::updateBook)
                .peek(updateBook -> log.info("update Book: {}", updateBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        UserDto userDto = userService.getUserById(userId);
        List<Long> bookIdList = bookService.getBookListByUserId(userId).stream().filter(Objects::nonNull).toList();
        log.info("Collected book ids: {}", bookIdList);
        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Deleted user with books: {}");
        bookService.getBookListByUserId(userId).forEach(bookService::deleteBookById);
        userService.deleteUserById(userId);
        log.info("Delete is completed: {}");
    }

    public UserBookResponse addBookToUserList(BookToListRequest bookToListRequest){
        BookDto bookDto = bookService.addBookToUserBookList(bookMapper.bookRequestToBookDto(bookToListRequest));
        log.info("New book in user's list: {}", bookDto);
        UserDto userDto = userService.getUserById(bookToListRequest.getUserId());
        List<Long> bookIdList = bookService.getBookListByUserId(bookToListRequest.getUserId())
                .stream()
                .filter(Objects::nonNull)
                .toList();
        log.info("Collected updated user's list: {}", bookIdList);
        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }
    public void deleteBook(Long bookId){
        log.info("Delete book in book's storage");
        bookService.getBookById(bookId);
    }
}
