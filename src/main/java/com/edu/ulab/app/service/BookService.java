package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto userDto);

    BookDto getBookById(Long id);

    List<Long> getBookListByUserId(long userId);
    void deleteBookById(Long id);

    BookDto addBookToUserBookList(BookDto bookDto);
}
