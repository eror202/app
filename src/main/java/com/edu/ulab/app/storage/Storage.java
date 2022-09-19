package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@Slf4j
public class Storage {

    private long userId = 1;
    private long bookId = 1;
    @Getter
    private Map<Long, User> userStorageInMemory = new HashMap<>();
    @Getter
    private Map<Long, Book> bookStorageInMemory = new HashMap<>();

    public User getUserById(long id) {
        User user = userStorageInMemory.get(id);
        if (user == null) {
           throw  new NotFoundException("User not found with id " + id);
        }

        return user;
    }

    public Book getBookById(long id) {
        Book book = bookStorageInMemory.get(id);
        if (book == null) {
          throw   new NotFoundException("Book not found with id " + id);
        }
        return book;
    }

    public Book addBookToStorage(Book book) {
        book.setId(bookId);
        User user = getUserById(book.getUserId());
        user.getBooksList().add(bookId);
        bookStorageInMemory.put(bookId, book);
        bookId++;
        return book;
    }

    public User addUserToStorage(User user) {
        user.setId(userId);
        userStorageInMemory.put(userId, user);
        user.setBooksList(new ArrayList<>());
        userId++;
        return user;
    }

    public User updateUser(User userToUpdate) {
        User user = getUserById(userToUpdate.getId());
        if (userToUpdate.getTitle().equals(null)) {
            userToUpdate.setTitle(user.getTitle());
        }
        if (userToUpdate.getAge() == 0) {
            userToUpdate.setAge(user.getAge());
        }
        if (userToUpdate.getFullName().equals(null)) {
            userToUpdate.setFullName(user.getFullName());
        }
        userStorageInMemory.put(userToUpdate.getId(), userToUpdate);
        return getUserById(userToUpdate.getId());
    }

    public Book updateBook(Book bookToUpdate) {
        Book book = getBookById(bookToUpdate.getId());
        if (bookToUpdate.getAuthor().equals(null)) {
            bookToUpdate.setAuthor(book.getAuthor());
        }
        if (bookToUpdate.getTitle().equals(null)) {
            bookToUpdate.setTitle(book.getTitle());
        }
        if (bookToUpdate.getPageCount() == 0) {
            bookToUpdate.setPageCount(book.getPageCount());
        }
        if (bookToUpdate.getUserId() == 0) {
            bookToUpdate.setUserId(book.getUserId());
        }
        bookStorageInMemory.put(bookToUpdate.getId(), bookToUpdate);
        return getBookById(bookToUpdate.getId());
    }

    public Book updateUserBookList(Book newBook){
        Book book = addBookToStorage(newBook);
        return book;
    }

    public List<Long> getBookListByUserId(long userId){
        User user = getUserById(userId);
        return user.getBooksList();
    }
    public void removeUser(long id) {
        userStorageInMemory.remove(id);
    }

    public void removeBook(long id) {
        bookStorageInMemory.remove(id);
    }
}
