package org.samuel.storemanagement.domain.product.ingredient.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.general.dto.BaseOption;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductIngredientRepositoryCustomImpl implements ProductIngredientRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductIngredientResponse> getAllCalculatedProducts(Long productId, Specification<ProductIngredient> specification) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductIngredientResponse> query = cb.createQuery(ProductIngredientResponse.class);
        Root<ProductIngredient> root = query.from(ProductIngredient.class);

        Join<ProductIngredient, Product> productJoin = root.join("ingredientProduct", JoinType.LEFT);
        Join<ProductIngredient, RawMaterial> rawMaterialJoin = root.join("rawMaterial", JoinType.LEFT);
        Join<ProductIngredient, Preparation> preparationJoin = root.join("preparation", JoinType.LEFT);

        Predicate predicate = cb.equal(root.get("product").get("id"), productId);

        Expression<Double> rawMaterialCostPerUnit = cb.toDouble(rawMaterialJoin.get("costPerUnit"));
        Expression<Double> preparationCostPerUnit = cb.toDouble(preparationJoin.get("costPerUnit"));
        Expression<Double> productCostPerUnit = cb.toDouble(productJoin.get("costPerUnit"));
        Expression<Double> quantity = cb.toDouble(root.get("quantity"));

        Expression<Object> totalCost = cb.selectCase()
                .when(cb.isNotNull(rawMaterialCostPerUnit), cb.prod(rawMaterialCostPerUnit, quantity))
                .when(cb.isNotNull(preparationCostPerUnit), cb.prod(preparationCostPerUnit, quantity))
                .when(cb.isNotNull(productCostPerUnit), cb.prod(productCostPerUnit, quantity))
                .otherwise(root.get("customCost"));

        Expression<Object> name = cb.selectCase()
                .when(productJoin.get("name").isNotNull(), productJoin.get("name"))
                .when(preparationJoin.get("name").isNotNull(), preparationJoin.get("name"))
                .when(rawMaterialJoin.get("name").isNotNull(), rawMaterialJoin.get("name"))
                .otherwise(root.get("customName"));

        Expression<Object> measurementUnit = cb.selectCase()
                .when(productJoin.get("measurementUnit").isNotNull(), productJoin.get("measurementUnit"))
                .when(preparationJoin.get("measurementUnit").isNotNull(), preparationJoin.get("measurementUnit"))
                .when(rawMaterialJoin.get("measurementUnit").isNotNull(), rawMaterialJoin.get("measurementUnit"))
                .otherwise(cb.literal(MeasurementUnit.PORTION));

        Expression<Object> measurementUnitOption = cb.selectCase()
                .when(cb.equal(measurementUnit, MeasurementUnit.KILOGRAMS),
                        cb.selectCase()
                                .when(cb.lessThan(quantity, 1d),
                                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.KILOGRAMS.ordinal()), cb.literal("g")))
                                .otherwise(
                                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.KILOGRAMS.ordinal()), cb.literal("kg"))
                                )
                )
                .when(cb.equal(measurementUnit, MeasurementUnit.LITER),
                        cb.selectCase()
                                .when(cb.lessThan(quantity, 1d),
                                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.LITER.ordinal()), cb.literal("ml")))
                                .otherwise(
                                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.LITER.ordinal()), cb.literal("L"))
                                )
                )
                .when(cb.equal(measurementUnit, MeasurementUnit.PORTION),
                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.PORTION.ordinal()), cb.literal("porção"))
                )
                .otherwise(
                        cb.construct(BaseOption.class, cb.literal((long) MeasurementUnit.UNIT.ordinal()), cb.literal("unidade(s)"))
                );

        query.select(cb.construct(
                ProductIngredientResponse.class,
                root.get("id"),
                totalCost,
                root.get("quantity"),
                name,
                measurementUnitOption
        ));
        query.where(cb.and(predicate, specification.toPredicate(root, query, cb)));

        return entityManager.createQuery(query).getResultList();
    }

    private CriteriaBuilder getCb() {
        return entityManager.getCriteriaBuilder();
    }
}
