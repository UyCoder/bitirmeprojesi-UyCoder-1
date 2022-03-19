package dev.ahmed.graduationproject.app.service.entityservice;

import dev.ahmed.graduationproject.app.dao.CategoryDao;
import dev.ahmed.graduationproject.app.dao.ProductDao;
import dev.ahmed.graduationproject.app.dto.ProductDto;
import dev.ahmed.graduationproject.app.dto.ProductSaveRequestDto;
import dev.ahmed.graduationproject.app.dto.ProductUpdatePriceDto;
import dev.ahmed.graduationproject.app.entity.Category;
import dev.ahmed.graduationproject.app.entity.Product;
import dev.ahmed.graduationproject.app.exception.ProductAlreadyExistsException;
import dev.ahmed.graduationproject.app.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Author Ahmed Bughra
 * @Created 3/17/2022 - 12:55 AM
 * @Project bitirmeprojesi-UyCoder
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ProductEntityService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;


    public Product insertProduct(ProductDto newProductDto){
        Optional<Product> productByName = productDao.findAllByProductName(newProductDto.getProductName());
        if (productByName.isPresent()){
            throw new ProductAlreadyExistsException("Product already exists with the name: " + newProductDto.getProductName());
        }
        Optional<Category> fromDbCategory = categoryDao.findAllByCategoryName(newProductDto.getCategoryName());
        Product newProduct = new Product();
        newProduct.setProductName(newProductDto.getProductName());
        newProduct.setCategoryId(fromDbCategory.get().getId());
        newProduct.setPriceWithoutKdv(newProductDto.getPriceWithoutKdv());
        newProduct.setKdvRate(fromDbCategory.get().getKdvRate());
        newProduct.setFinalPrice(newProductDto.getPriceWithoutKdv()
                .multiply(BigDecimal.valueOf(fromDbCategory.get().getKdvRate()))
                .divide(BigDecimal.valueOf(100))
                .add(newProductDto.getPriceWithoutKdv()));
        return productDao.save(newProduct);
    }

    public Product createProduct(Product newProduct){
        Optional<Product> productByName = productDao.findAllByProductName(newProduct.getProductName());
        if (productByName.isPresent()){
            throw new ProductAlreadyExistsException("Product already exists with the name: " + newProduct.getProductName());
        }

        return productDao.save(newProduct);
    }

    public void updatesProductById(Long id, ProductSaveRequestDto productSaveRequestDto){
        Product oldProduct = getProductById(id);
        oldProduct.setProductName(productSaveRequestDto.getProductName());
//        oldProduct.setCategoryId(productSaveRequestDto.getCategoryId());
        oldProduct.setPriceWithoutKdv(productSaveRequestDto.getPriceWithoutKdv());
//        oldProduct.setKdvRate(productSaveRequestDto.getKdvRate());
//        oldProduct.setFinalPrice(productSaveRequestDto.getFinalPrice());
        productDao.save(oldProduct);
    }

    public void updatesPriceById(Long id, ProductUpdatePriceDto productUpdatePriceDto){
        Product oldProduct = getProductById(id);
        oldProduct.setPriceWithoutKdv(productUpdatePriceDto.getPriceWithoutKdv());
        productDao.save(oldProduct);
    }



    public Product getProductById(Long id){
        return productDao.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with that id: "+id));
    }


    public void deleteProduct(Long id) {
        Product product = productDao.getById(id);
        productDao.delete(product);
    }


}
