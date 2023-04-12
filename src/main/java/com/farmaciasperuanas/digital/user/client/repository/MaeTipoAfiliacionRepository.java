package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.entity.MaeTipoAfiliacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaeTipoAfiliacionRepository extends JpaRepository<MaeTipoAfiliacionEntity, String> {
    public List<MaeTipoAfiliacionEntity> findByCoAfiliacion(String coAfiliacion);
}
