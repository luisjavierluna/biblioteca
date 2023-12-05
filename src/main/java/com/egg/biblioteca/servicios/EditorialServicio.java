package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList();
        
        editoriales = editorialRepositorio.findAll();
        
        return editoriales;
    }
    
    public Editorial getOne(String id) {
        return editorialRepositorio.getOne(id);
    }
    
    @Transactional
    public void crearEditorial(String nombre) {
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void modificarEditorial(String nombre, String id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }
    }
    
    @Transactional
    public void eliminarEditorial(String id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            editorialRepositorio.delete(editorial);
        }
    }
    
}
