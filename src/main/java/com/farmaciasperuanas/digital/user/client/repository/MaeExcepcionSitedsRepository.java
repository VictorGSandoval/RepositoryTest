package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.entity.MaeExcepcionSitedsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaeExcepcionSitedsRepository extends JpaRepository<MaeExcepcionSitedsEntity, String> {
    public List<MaeExcepcionSitedsEntity> findByCoExcepcion(String coExcepcion);
}
