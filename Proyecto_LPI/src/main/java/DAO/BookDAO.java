package dao;

import connection.DatabaseConnection;
import model.Book;
import model.Author;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Listar libros por categoría
    public static List<Book> listByCategory(String categoryName) {

        List<Book> books = new ArrayList<>();

        String sql = """
            SELECT b.book_id, b.title, b.description, b.cover_image_url,
                   a.author_id, a.full_name AS author_name,
                   c.category_id, c.category_name
            FROM book b
            JOIN author a ON b.author_id = a.author_id
            JOIN category c ON b.category_id = c.category_id
            WHERE c.category_name = ?
        """;

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setTitle(rs.getString("title"));
                book.setDescription(rs.getString("description"));
                book.setCoverImageUrl(rs.getString("cover_image_url"));

                Author author = new Author();
                author.setAuthorId(rs.getString("author_id"));
                author.setFullName(rs.getString("author_name"));
                book.setAuthor(author);

                Category category = new Category();
                category.setCategoryId(rs.getString("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                book.setCategory(category);

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    //  Buscar libro por ID
    public static Book findById(String bookId) {

        Book book = null;

        String sql = """
            SELECT b.*, 
                   a.full_name AS author_name,
                   c.category_name
            FROM book b
            JOIN author a ON b.author_id = a.author_id
            JOIN category c ON b.category_id = c.category_id
            WHERE b.book_id = ?
        """;

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setTitle(rs.getString("title"));
                book.setDescription(rs.getString("description"));
                book.setCoverImageUrl(rs.getString("cover_image_url"));
                book.setPublicationYear(rs.getInt("publication_year"));
                book.setLanguage(rs.getString("language"));

                Author author = new Author();
                author.setAuthorId(rs.getString("author_id"));
                author.setFullName(rs.getString("author_name"));
                book.setAuthor(author);

                Category category = new Category();
                category.setCategoryId(rs.getString("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                book.setCategory(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }
}
