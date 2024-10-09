package com.simonf.ecommerce.config;

import com.simonf.ecommerce.entity.Product;
import com.simonf.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public void MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        // Determine the HTTP methods that need to be disabled in order for our API endpoints to be read-only
        HttpMethod[] unsupportedActions = { HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };

        // Disable HTTP methods for Product
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));

        // Disable HTTP methods for ProductCategory
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));

        // Call an internal helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // Get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // Create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // Get the entity types for the entities
        for (EntityType tempEntityType:entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // Expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}