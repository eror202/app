package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final Storage storage;
    private final BookMapper bookMapper;
    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = storage.addBookToStorage(bookMapper.bookDtoToBook(bookDto));
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = storage.updateBook(bookMapper.bookDtoToBook(bookDto));
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.bookToBookDto(storage.getBookById(id));
    }

    @Override
    public List<Long> getBookListByUserId(long userId) {
        return storage.getBookListByUserId(userId);
    }

    @Override
    public void deleteBookById(Long id) {
        storage.removeBook(id);
    }

    @Override
    public BookDto addBookToUserBookList(BookDto bookDto) {
        Book book = storage.updateUserBookList(bookMapper.bookDtoToBook(bookDto));
        return bookMapper.bookToBookDto(book);
    }
}
