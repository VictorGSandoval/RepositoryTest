package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.entity.MaeErrorSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaeErrorSolicitudRepository extends JpaRepository<MaeErrorSolicitudEntity, String> {
    public List<MaeErrorSolicitudEntity> findByCoIndicadorErrorAndCoError(String coIndicadorError, String coError);
}
