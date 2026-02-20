package com.practica01.service;

import com.practica01.domain.Arbol;
import com.practica01.repository.ArbolRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArbolService {

    private final ArbolRepository arbolRepository;
    private final FirebaseStorageService firebaseStorageService;

    public ArbolService(ArbolRepository arbolRepository,
            FirebaseStorageService firebaseStorageService) {
        this.arbolRepository = arbolRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Arbol> getArboles() {
        return arbolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Arbol> getArbol(Integer idArbol) {
        return arbolRepository.findById(idArbol);
    }

    @Transactional
    public void save(Arbol arbol, MultipartFile imagenFile) {

        arbol = arbolRepository.save(arbol);

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile,
                        "arbol",
                        arbol.getIdArbol()
                );
                arbol.setRutaImagen(rutaImagen);
                arbolRepository.save(arbol);
            } catch (IOException e) {
                
            }
        }
    }

    @Transactional
    public void delete(Integer idArbol) {

        if (!arbolRepository.existsById(idArbol)) {
            throw new IllegalArgumentException(
                    "El árbol con ID " + idArbol + " no existe."
            );
        }

        try {
            arbolRepository.deleteById(idArbol);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el árbol. Tiene datos asociados.", e
            );
        }
    }
}
