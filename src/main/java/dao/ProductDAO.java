package dao;

import entity.Product;
import entity.ProductDetails;
import entity.ProductCategory;
import form.ProductForm;
import java.io.IOException;
import java.util.Date;

import javax.persistence.NoResultException;
import model.ProductInfo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pagination.PaginationResult;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Product findProduct(String code) {
        try {
            String sql = "Select e from " + Product.class.getName() + " e Where e.code =:code ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("code", code);
            return (Product) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductDetails findProductDetails(String code) {
        try {
            String sql = "Select e from " + ProductDetails.class.getName() + " e Where e.product =:code ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<ProductDetails> query = session.createQuery(sql, ProductDetails.class);
            query.setParameter("code", code);
            return (ProductDetails) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        ProductDetails productDetails = this.findProductDetails(code);
        if (product == null || productDetails == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice(), product.getProductCategory(), productDetails.getDescription(), productDetails.getSpecification());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        String code = productForm.getCode();

        Product product = null;
        ProductDetails productDetails = null;

        boolean isNewProduct = false;
        boolean isNewProductDetails = false;

        if (code != null) {
            product = this.findProduct(code);
            productDetails = this.findProductDetails(code);
        }
        if (product == null) {
            isNewProduct = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        if (productDetails == null) {
            productDetails = new ProductDetails();
            isNewProductDetails = true;
        }
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setProductCategory(productForm.getProductCategory());

        productDetails.setProduct(code);
        productDetails.setDescription(productForm.getDescription());
        productDetails.setSpecification(productForm.getSpecification());

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNewProduct) {
            session.persist(product);
        }
        if (isNewProductDetails) {
            saveProductDetails(productDetails);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
            String likeName, int prodCat, String code) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price, p.productCategory, pd.description, pd.specification) " + " from "//
                + Product.class.getName() + " p "
                + " inner join " + ProductCategory.class.getName() + " pc on p.productCategory = pc.id "
                + " inner join " + ProductDetails.class.getName() + " pd on pd.product = p.code ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        if (code != null && code.length() > 0) {
            sql += sql.contains("Where") ? " and p.code=:code " : " Where p.code=:code ";
        }
        if (prodCat > 0) {
            sql += sql.contains("Where") ? " and pc.id=:prodCat " : " Where pc.id=:prodCat ";
        }
        sql += " order by p.createDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<ProductInfo> query = session.createQuery(sql, ProductInfo.class);

        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        if (code != null && code.length() > 0) {
            query.setParameter("code", code);
        }
        if (prodCat > 0) {
            query.setParameter("prodCat", String.valueOf(prodCat));
        }
        return new PaginationResult<>(query, page, maxResult, maxNavigationPage);
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null, 0, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProductDetails(ProductDetails productDetails) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(productDetails);
        session.flush();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteProduct(String code){
        Session session = this.sessionFactory.getCurrentSession();
        Product product = null;
        if (code != null) {
            product = this.findProduct(code);
            session.delete(product);
        }
        session.flush();
    }
}
