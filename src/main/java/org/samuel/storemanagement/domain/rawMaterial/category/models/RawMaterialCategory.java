package org.samuel.storemanagement.domain.rawMaterial.category.models;

import jakarta.persistence.*;
import lombok.Data;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "raw_materials_categories")
@Data
public class RawMaterialCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean hasAssociation;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<RawMaterial> products = new ArrayList<>();
}
