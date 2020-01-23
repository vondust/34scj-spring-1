package br.com.fiap.library.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.library.dto.AutorDTO;
import br.com.fiap.library.dto.BookDTO;
import br.com.fiap.library.dto.CreateBookDTO;

@Controller
@RequestMapping("livros")
public class BookController {

    private static List<BookDTO> books = getBooks();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateBookDTO createBookDto) {
        BookDTO book = new BookDTO(createBookDto);
        book.setId(books.size() + 1);
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String titulo) {
        return ResponseEntity.ok(books.stream().filter(book -> titulo == null || book.getTitulo().contains(titulo))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(required = true) Integer id) {
        Optional<BookDTO> bookDto = books.stream().filter(book -> book.getId() == id).findFirst();
        return bookDto.isPresent() ? ResponseEntity.ok(bookDto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(required = true) Integer id, @RequestBody CreateBookDTO createBookDto) {
        Optional<BookDTO> bookDto = books.stream().filter(book -> book.getId() == id).findFirst();
        if (!bookDto.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        BookDTO book = bookDto.get();
        book.setTitulo(createBookDto.getTitulo());
        book.setQuantidadeDePaginas(createBookDto.getQuantidadeDePaginas());
        book.setISBN(createBookDto.getISBN());
        book.setDataLancamento(createBookDto.getDataLancamento());

        return ResponseEntity.ok(book);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(required = true) Integer id, @RequestBody AutorDTO autorDTO) {
        Optional<BookDTO> bookDto = books.stream().filter(book -> book.getId() == id).findFirst();
        if (!bookDto.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        BookDTO book = bookDto.get();
        book.setAutor(autorDTO);

        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(required = true) Integer id) {
        Optional<BookDTO> bookDto = books.stream().filter(book -> book.getId() == id).findFirst();
        if (!bookDto.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        books.remove(bookDto.get());
    }

    private static List<BookDTO> getBooks() {
        BookDTO book1 = BookDTO.builder()
            .id(1)
            .titulo("Kingkiller")
            .quantidadeDePaginas(800)
            .ISBN("65465486454")
            .dataLancamento(ZonedDateTime.now().minusYears(40))
            .autor(new AutorDTO())
            .build();

        BookDTO book2 = BookDTO.builder()
            .id(2)
            .titulo("Steppenwolf")
            .quantidadeDePaginas(200)
            .ISBN("65465486455")
            .dataLancamento(ZonedDateTime.now().minusYears(47))
            .autor(new AutorDTO())
            .build();
            
        BookDTO book3 = BookDTO.builder()
            .id(3)
            .titulo("Silmarilion")
            .quantidadeDePaginas(900)
            .ISBN("65465486456")
            .dataLancamento(ZonedDateTime.now().minusYears(38))
            .autor(new AutorDTO())
            .build();

        return new ArrayList<>(Arrays.asList(book1, book2, book3));
    }
}