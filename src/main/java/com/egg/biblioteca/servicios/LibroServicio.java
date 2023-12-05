package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    LibroRepositorio libroRepositorio;
    
    @Autowired
    AutorRepositorio autorRepositorio;
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    public Libro getOne(Long isbn) {
        return libroRepositorio.getOne(isbn);
    }
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) {
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idAutor).get();
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) {
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()) 
            autor = respuestaAutor.get();
        
        if (respuestaEditorial.isPresent()) 
            editorial = respuestaEditorial.get();
                
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);

            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);
        }
    }
    
    @Transactional
    public void eliminarLibro(Long isbn) {
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            
            Optional<Autor> respuestaAutor = autorRepositorio.findById(libro.getAutor().getId());
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(libro.getEditorial().getId());
            
            Autor autor = new Autor();
            Editorial editorial = new Editorial();
            
            if (respuestaAutor.isPresent()) autor = respuestaAutor.get(); 
            if (respuestaEditorial.isPresent()) editorial = respuestaEditorial.get();

            autorRepositorio.delete(autor);
            editorialRepositorio.delete(editorial);
            libroRepositorio.delete(libro);
        }
    }
    
    
}
