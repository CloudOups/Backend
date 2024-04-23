package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Produit;
import tn.esprit.pi.repositories.IProduitRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProduitService implements IProduitService {

    private final IProduitRepository produitRepository;
    @Override
    public Produit addProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Produit updateProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public void delete(Long numProd) {
        produitRepository.deleteById(numProd);
    }

    @Override
    public Produit getById(Long numProd) {
        return produitRepository.findById(numProd).orElse(null);
    }

    @Override
    public List<Produit> getAll() {
        return  produitRepository.findAll();
    }

    @Override
    public Page<Produit> getAllPaggination(Pageable pageable) {
        return  produitRepository.findAll(pageable);
    }

    @Override
    public Page<Produit> findByNomAndDescription(String keyord1, Pageable pageable) {
        return produitRepository.findByNomProdContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyord1, keyord1, pageable);
    }
}
