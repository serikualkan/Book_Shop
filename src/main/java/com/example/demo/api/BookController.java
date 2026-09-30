package com.example.demo.api;

import com.example.demo.Dto.BookDto;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin/books")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<BookDto> create(
            @RequestParam("title") String title,
            @RequestParam("author") String author,

            @RequestParam(value = "genre", required = false) String genre,

            @RequestParam(value = "categoryName", required = false) String categoryName,

            @RequestParam("year") Integer year,
            @RequestParam("price") Double price,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        BookDto dto = new BookDto();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setGenre(genre);
        dto.setYear(year);
        dto.setPrice(price);

        dto.setCategoryName(categoryName != null ? categoryName : genre);

        if (imageFile != null && !imageFile.isEmpty()) {
            String folder = "uploads/";
            Path uploadPath = Paths.get(folder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" +
                    imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.]", "_");

            Path path = uploadPath.resolve(filename);
            Files.write(path, imageFile.getBytes());

            dto.setImageUrl("/" + folder + filename);
        }

        return ResponseEntity.ok(bookService.create(dto));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<BookDto> update(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("author") String author,

            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "categoryName", required = false) String categoryName,

            @RequestParam("year") Integer year,
            @RequestParam("price") Double price,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        BookDto dto = new BookDto();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setGenre(genre);
        dto.setYear(year);
        dto.setPrice(price);

        dto.setCategoryName(categoryName != null ? categoryName : genre);

        if (imageFile != null && !imageFile.isEmpty()) {
            String folder = "uploads/";
            Path uploadPath = Paths.get(folder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            BookDto existingBook = bookService.getById(id);
            if (existingBook.getImageUrl() != null) {
                Path oldFilePath = Paths.get(existingBook.getImageUrl().replaceFirst("/", ""));
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
            }

            String filename = System.currentTimeMillis() + "_" +
                    imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.]", "_");

            Path path = uploadPath.resolve(filename);
            Files.write(path, imageFile.getBytes());

            dto.setImageUrl("/" + folder + filename);
        }

        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
